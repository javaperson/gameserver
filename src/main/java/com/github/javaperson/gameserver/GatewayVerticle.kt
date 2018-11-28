package com.github.javaperson.gameserver

import com.github.javaperson.gameserver.anno.HeService
import com.github.javaperson.gameserver.proto.Echo

@HeService("gateway")
class GatewayVerticle : BaseVerticle(), GatewayHandler {
    override suspend fun returnNull(): Echo? {
        return null
    }

    override suspend fun echo(msg: String, age: Int): Echo {
        return Echo(age, msg)
    }

    override fun unique(): Boolean {
        return true
    }
}
