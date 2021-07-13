@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.dlopezgarsco.api.v1

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*


@Location("/api/v1/tickets")
class Tickets {
  @Location("/{id}")
  data class Single(val parent: Tickets, val id: Long)
}

fun Route.tickets() {
  get<Tickets> {
    call.respondText("All tickets")
  }
  get<Tickets.Single> {
    call.respondText("Ticket #${it.id}")
  }
}