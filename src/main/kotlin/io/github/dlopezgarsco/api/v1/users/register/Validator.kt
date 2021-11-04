package io.github.dlopezgarsco.api.v1.users.register

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.computations.either
import io.github.dlopezgarsco.api.v1.users.UserPayload
import io.github.dlopezgarsco.db.models.UserDAO
import io.github.dlopezgarsco.utils.ValidationType
import io.github.dlopezgarsco.utils.Validator


sealed class RegisterError {
  object EmptyInput : RegisterError()
  object InvalidUser : RegisterError()
  object UserExists : RegisterError()
}

suspend fun UserPayload.validate(): Either<RegisterError, UserPayload> = either {
  input().bind()
  database().bind()
}

private fun UserPayload.input(): Either<RegisterError, UserPayload> = when {
  user.isEmpty() || password.isEmpty() -> Left(RegisterError.EmptyInput)
  !Validator.verify(user, ValidationType.EMAIL) -> Left(RegisterError.InvalidUser)
  else -> Right(this)
}

private suspend fun UserPayload.database(): Either<RegisterError, UserPayload> = when {
  UserDAO.getByUser(user).isNotEmpty() -> Left(RegisterError.UserExists)
  else -> Right(this)
}
