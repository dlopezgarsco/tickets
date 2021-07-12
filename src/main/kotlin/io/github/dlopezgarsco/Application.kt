package io.github.dlopezgarsco

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.github.dlopezgarsco.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSecurity()
        configureRouting()
        configureHTTP()
        configureSerialization()
    }.start(wait = true)
}
