@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.dlopezgarsco.api.v1.users

import io.github.dlopezgarsco.api.v1.users.login.LoginController
import io.github.dlopezgarsco.api.v1.users.register.RegisterController
import io.ktor.locations.*
import io.ktor.locations.delete
import io.ktor.locations.post
import io.ktor.locations.patch
import io.ktor.routing.*

data class UserPayload(val user: String, val password: String)


@Location("/api/v1/users")
class Users {
  @Location("/register")
  class Register
}

fun Route.users() {

  post<Users> { LoginController.login(this) }

  post<Users.Register> { RegisterController.register(this) }
  patch<Users.Register> { RegisterController.patch(this) }
  delete<Users.Register> { RegisterController.delete(this) }

  get<Users.Register> { RegisterController.all(this) }
}