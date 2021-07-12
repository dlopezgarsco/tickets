package io.github.dlopezgarsco

import io.github.dlopezgarsco.plugins.configureHTTP
import io.github.dlopezgarsco.plugins.configureRouting
import io.github.dlopezgarsco.plugins.configureSecurity
import io.github.dlopezgarsco.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
  configureSecurity()
  configureRouting()
  configureHTTP()
  configureSerialization()
}
