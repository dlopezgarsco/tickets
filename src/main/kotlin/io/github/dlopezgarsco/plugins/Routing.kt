package io.github.dlopezgarsco.plugins

import io.github.dlopezgarsco.api.v1.*
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
  install(Locations)

  routing {
    tickets()
    login()
  }
}
