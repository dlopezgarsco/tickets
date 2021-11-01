package io.github.dlopezgarsco.api.v1.users

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.computations.either
import arrow.core.getOrElse
import io.github.dlopezgarsco.db.models.UserDAO
import io.github.dlopezgarsco.utils.ValidationType
import io.github.dlopezgarsco.utils.Validator


sealed class LoginError {
  object EmptyInput : LoginError()
  object InvalidUser : LoginError()
  object UserNotFound : LoginError()
  object IncorrectPassword : LoginError()
}

object LoginValidator {

  suspend fun LoginPayload.validate(): Either<LoginError, LoginPayload> = either {
    input().bind()
    database().bind()
  }

  private fun LoginPayload.input(): Either<LoginError, LoginPayload> = when {
    user.isEmpty() || pass.isEmpty() -> Left(LoginError.EmptyInput)
    !Validator.verify(user, ValidationType.EMAIL) -> Left(LoginError.InvalidUser)
    else -> Right(this)
  }

  private suspend fun LoginPayload.database(): Either<LoginError, LoginPayload> {
    val row = UserDAO.getByUser(user).getOrElse { return Left(LoginError.UserNotFound) }
    return when {
      this.pass != row.password -> Left(LoginError.IncorrectPassword)
      else -> Right(this)
    }
  }

}