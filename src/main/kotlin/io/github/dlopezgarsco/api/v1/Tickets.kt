@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.dlopezgarsco.api.v1

import io.github.dlopezgarsco.plugins.JWTService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


@Location("/api/v1/tickets")
class Tickets {

  @Location("/{id}")
  data class Single(val parent: Tickets, val id: Long)
}

fun Route.tickets() {

  get<Tickets> {
    val token = JWTService.INSTANCE.generateAnonymousToken(
      mapOf("Host" to call.request.host(), "User-Agent" to call.request.userAgent().orEmpty())
    )
    call.response.headers.append("Authorization", "Bearer $token")
    call.respondText { "All tickets" }
  }

  authenticate("anon") {
    get<Tickets.Single> {
      call.respondText("Ticket #${it.id}")
    }
  }

}