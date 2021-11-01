package io.github.dlopezgarsco.plugins

import io.github.dlopezgarsco.api.v1.tickets
import io.github.dlopezgarsco.api.v1.users.users
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*

fun Application.configureRouting() {
  install(Locations)

  routing {
    tickets()
    users()
  }
}
