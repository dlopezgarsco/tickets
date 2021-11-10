package io.github.dlopezgarsco.api.v1.users.register

import arrow.core.Either
import arrow.core.handleError
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
    when (val result = body.validateCreate()) {
      is Either.Left -> {
        ctx.call.response.status(HttpStatusCode.BadRequest)
        ctx.call.respond(result.mapLeft { it.javaClass })
      }
      is Either.Right -> {
        UserDAO.create(User(user = body.user, password = body.password))
        ctx.call.respond(HttpStatusCode.OK)
      }
    }
  }

  suspend fun delete(ctx: PipelineContext<Unit, ApplicationCall>) {
    val body = ctx.call.receive<UserPayload>()
    when (body.validateUpdate()) {
      is Either.Left -> ctx.call.respond(HttpStatusCode.BadRequest)
      is Either.Right -> {
        when (UserDAO.delete(User(user = body.user))) {
          false -> ctx.call.respond(HttpStatusCode.NotFound)
          true -> ctx.call.respond(HttpStatusCode.OK)
        }
      }
    }
  }

  suspend fun patch(ctx: PipelineContext<Unit, ApplicationCall>) {
    val body = ctx.call.receive<UserPayload>()
    when (val result = body.validateUpdate()) {
      is Either.Left -> {
        ctx.call.response.status(HttpStatusCode.BadRequest)
        ctx.call.respond(result.mapLeft { it.javaClass })
      }
      is Either.Right -> {
        when (UserDAO.update(User(user = body.user, password = body.password))) {
          false -> ctx.call.respond(HttpStatusCode.NotFound)
          true -> ctx.call.respond(HttpStatusCode.OK)
        }
      }
    }
  }

  suspend fun all(ctx: PipelineContext<Unit, ApplicationCall>) {
    ctx.call.respond(UserDAO.getAll())
  }
}