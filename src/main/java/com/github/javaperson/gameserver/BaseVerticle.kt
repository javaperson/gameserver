package com.github.javaperson.gameserver

import com.github.javaperson.gameserver.anno.HeHandler
import com.github.javaperson.gameserver.anno.HeService
import io.vertx.core.AbstractVerticle
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.findAnnotation

open class BaseVerticle : AbstractVerticle() {
    private val handlers = mutableMapOf<Int, KCallable<*>>()

    protected open fun unique(): Boolean {
        return false
    }

    override fun start() {
        val clazz = this::class
        for (type in clazz.supertypes) {
            val c = type.classifier
            if (c is KClass<*>) {
                if (c.findAnnotation<HeHandler>() != null) {
                    for (member in c.members) {
                        val anno = member.findAnnotation<HeHandler>()
                        val id = anno?.id
                        if (id != null) {
                            handlers[id] = member
                        }
                    }
                }
            }
        }


        var address = deploymentID()
        if (unique()) {
            val annotation = clazz.findAnnotation<HeService>()
            handler(annotation?.name)
        }
        handler(address)
    }

    private fun handler(address: String?) {
        if (address.isNullOrEmpty()) {
            throw RuntimeException("address is null or empty.")
        }
        val eventBus = vertx.eventBus()
        eventBus.consumer<InvocationMessage>(address) { msg ->
            GlobalScope.launch(vertx.dispatcher()) { msg.reply(invoke(msg.body()), Constants.BASE_DELIVERY_OPTIONS) }
        }
    }

    private suspend fun invoke(im: InvocationMessage): Any? {
        val h = handlers[im.id]
        var result = h!!.callSuspend(this, *im.args)
        return ReturnMessage(result)
    }
}
