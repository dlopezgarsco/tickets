package io.github.dlopezgarsco.api.v1.users.login

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.computations.either
import arrow.core.getOrElse
import io.github.dlopezgarsco.api.v1.users.UserPayload
import io.github.dlopezgarsco.db.models.UserDAO
import io.github.dlopezgarsco.utils.ValidationType
import io.github.dlopezgarsco.utils.Validator

sealed class LoginError {
  object EmptyInput : LoginError()
  object InvalidUser : LoginError()
  object UserNotFound : LoginError()
  object IncorrectPassword : LoginError()
}

suspend fun UserPayload.validate(): Either<LoginError, UserPayload> = either {
  input().bind()
  database().bind()
}

private fun UserPayload.input(): Either<LoginError, UserPayload> = when {
  user.isEmpty() || password.isEmpty() -> Left(LoginError.EmptyInput)
  !Validator.verify(user, ValidationType.EMAIL) -> Left(LoginError.InvalidUser)
  else -> Right(this)
}

private suspend fun UserPayload.database(): Either<LoginError, UserPayload> {
  val row = UserDAO.getByUser(user).getOrElse { return Left(LoginError.UserNotFound) }
  return when {
    this.password != row.password -> Left(LoginError.IncorrectPassword)
    else -> Right(this)
  }
}
