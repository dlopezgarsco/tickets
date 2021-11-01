package io.github.dlopezgarsco.api.v1.users

import arrow.core.Either
import io.github.dlopezgarsco.api.v1.users.LoginValidator.validate
import io.github.dlopezgarsco.plugins.JWTService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

object LoginController {
  suspend fun login(ctx: PipelineContext<Unit, ApplicationCall>) {
    val body = ctx.call.receive<LoginPayload>()
    when (body.validate()) {
      is Either.Left -> {
        ctx.call.respond(HttpStatusCode.BadRequest)
      }
      is Either.Right -> {
        val token = JWTService.INSTANCE.generateToken(body.user)
        ctx.call.response.headers.append("Authorization", "Bearer $token")
        ctx.call.respond(HttpStatusCode.OK)
      }
    }
  }
}