package io.github.dlopezgarsco.api.v1.users.register

import arrow.core.Either
import io.github.dlopezgarsco.api.v1.users.UserPayload
import io.github.dlopezgarsco.db.models.User
import io.github.dlopezgarsco.db.models.UserDAO
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

object RegisterController {
  suspend fun register(ctx: PipelineContext<Unit, ApplicationCall>) {
    val body = ctx.call.receive<UserPayload>()
    when (body.validate()) {
      is Either.Left -> {
        ctx.call.respond(HttpStatusCode.BadRequest)
      }
      is Either.Right -> {
        UserDAO.create(User(
          user = body.user, password = body.password)
        )
        ctx.call.respond(HttpStatusCode.OK)
      }
    }
  }

  suspend fun all(ctx: PipelineContext<Unit, ApplicationCall>) {
    ctx.call.respond(UserDAO.getAll())
  }
}