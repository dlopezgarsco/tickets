package io.github.dlopezgarsco.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.*

fun Application.configureSecurity() {

  JWTService.create(
    environment.config.property("jwt.audience").getString(),
    environment.config.property("jwt.issuer").getString(),
    environment.config.property("jwt.secret").getString(),
    environment.config.property("jwt.validity").getString().toLong()
  )

  authentication {
    jwt("anon") {
      val jwtAudience = environment.config.property("jwt.audience").getString()
      realm = environment.config.property("jwt.realm").getString()
      verifier(
        JWT
          .require(Algorithm.HMAC256(environment.config.property("jwt.secret").getString()))
          .withAudience(jwtAudience)
          .withIssuer(environment.config.property("jwt.issuer").getString())
          .build()
      )
      validate { credential ->
        if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
      }
    }
  }
}

class JWTService private constructor(
  private val audience: String,
  private val issuer: String,
  private val secret: String,
  private val validity: Long,
) {
  companion object Factory {
    lateinit var INSTANCE: JWTService

    fun create(audience: String, issuer: String, secret: String, validity: Long): JWTService {
      this.INSTANCE = JWTService(audience, issuer, secret, validity)
      return INSTANCE
    }
  }

  fun generateToken(user: String): String = JWT.create()
    .withAudience(audience)
    .withSubject("Authentication")
    .withClaim("User", user)
    .withIssuer(issuer)
    .withExpiresAt(obtainExpirationDate())
    .sign(Algorithm.HMAC256(secret))

  fun generateAnonymousToken(claims: Map<String, String>): String = JWT.create()
    .withAudience(audience)
    .withSubject("Authentication")
    .withClaim("Host", claims["Host"])
    .withClaim("User-Agent", claims["User-Agent"])
    .withIssuer(issuer)
    .withExpiresAt(obtainExpirationDate())
    .sign(Algorithm.HMAC256(secret))

  private fun obtainExpirationDate() = Date(System.currentTimeMillis() + validity)
}