package io.github.dlopezgarsco.plugins

import io.ktor.http.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureHTTP() {
  install(CORS) {
    method(HttpMethod.Get)
    method(HttpMethod.Post)
    method(HttpMethod.Put)
    method(HttpMethod.Patch)
    method(HttpMethod.Delete)
    method(HttpMethod.Options)
    header(HttpHeaders.Authorization)
    allowCredentials = true
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
  }
  install(DefaultHeaders)

}
