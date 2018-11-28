package com.github.javaperson.gameserver

import io.vertx.core.Vertx
import io.vertx.core.Vertx.clusteredVertx
import io.vertx.core.VertxOptions
import io.vertx.kotlin.coroutines.awaitResult
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GlobalScope.launch {
                val vertx = awaitResult<Vertx> { h -> clusteredVertx(VertxOptions(), h) }
                val eventBus = vertx.eventBus()
                eventBus.registerCodec(KryoMessageCodec.INSTANCE)
                val id = awaitResult<String> { h -> vertx.deployVerticle(GatewayVerticle(), h) }
                println(id)
                vertx.setPeriodic(2000) {
                    println("tick")
                    GlobalScope.launch(vertx.dispatcher()) {

                        val handler = GatewayHandlerImpl(vertx, "gateway")
                        println(handler.echo("hello", 10))
                        println(handler.returnNull())
                    }
                }
            }

            Thread.sleep(30000)
        }
    }
}