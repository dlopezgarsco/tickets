@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.dlopezgarsco.api.v1

import io.github.dlopezgarsco.plugins.JWTService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

@Location("/api/v1/login")
class Login

private data class RequestPayload(val user: String, val pass: String)

fun Route.login() {

  post<Login> {
    val body = call.receive<RequestPayload>()
    val token = JWTService.INSTANCE.generateToken(body.user)
    call.response.headers.append("Authorization", "Bearer $token")
    call.respond(HttpStatusCode.OK)
  }

}
