package com.github.javaperson.gameserver

import com.github.javaperson.gameserver.anno.HeHandler
import com.github.javaperson.gameserver.proto.Echo

@HeHandler
interface GatewayHandler {
    @HeHandler(id = 1)
    suspend fun echo(msg: String, age: Int): Echo

    @HeHandler(id = 2)
    suspend fun returnNull(): Echo?
}
