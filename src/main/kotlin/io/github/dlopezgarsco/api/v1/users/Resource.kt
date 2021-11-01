@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.dlopezgarsco.api.v1.users

import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.routing.*

@Location("/api/v1/users")
class Users {
  @Location("/register")
  class Register
}

fun Route.users() {

  post<Users> { LoginController.login(this) }

  post<Users.Register> {}
}