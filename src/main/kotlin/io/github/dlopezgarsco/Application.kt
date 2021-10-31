package io.github.dlopezgarsco

import io.github.dlopezgarsco.plugins.*
import io.ktor.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
  configureSecurity()
  configureRouting()
  configureHTTP()
  configureSerialization()
  configureMonitoring()
  configureDatabase()
}
