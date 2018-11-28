package com.github.javaperson.gameserver

import com.github.javaperson.gameserver.Constants.BASE_DELIVERY_OPTIONS
import com.github.javaperson.gameserver.proto.Echo
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import io.vertx.kotlin.coroutines.awaitResult

class GatewayHandlerImpl : GatewayHandler {
    override suspend fun returnNull(): Echo? {
        val awaitResult = awaitResult<Message<ReturnMessage>> { h ->
            vertx.eventBus().send(address, InvocationMessage(2, arrayOf()), BASE_DELIVERY_OPTIONS, h)
        }
        val body = awaitResult.body()
        return body?.result as Echo?
    }

    private val vertx: Vertx
    private val address: String

    constructor(vertx: Vertx, address: String) {
        this.vertx = vertx
        this.address = address
    }

    override suspend fun echo(msg: String, age: Int): Echo {
        val awaitResult = awaitResult<Message<ReturnMessage>> { h ->
            vertx.eventBus().send(address, InvocationMessage(1, arrayOf(msg, age)), BASE_DELIVERY_OPTIONS, h)
        }
        return awaitResult.body().result as Echo
    }
}