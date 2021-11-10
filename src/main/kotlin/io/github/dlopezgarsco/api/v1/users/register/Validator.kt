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
  object UserDoesNotExist : RegisterError()
}

suspend fun UserPayload.validateCreate(): Either<RegisterError, UserPayload> = either {
  input().bind()
  userDoesNotExist().bind()
}

suspend fun UserPayload.validateUpdate(): Either<RegisterError, UserPayload> = either {
  input().bind()
  userExists().bind()
}

private fun UserPayload.input(): Either<RegisterError, UserPayload> = when {
  user.isEmpty() || password.isEmpty() -> Left(RegisterError.EmptyInput)
  !(Validator.verify(user, ValidationType.EMAIL)) -> Left(RegisterError.InvalidUser)
  else -> Right(this)
}

private suspend fun UserPayload.userDoesNotExist(): Either<RegisterError, UserPayload> = when {
  UserDAO.getByUser(user).isNotEmpty() -> Left(RegisterError.UserExists)
  else -> Right(this)
}

private suspend fun UserPayload.userExists(): Either<RegisterError, UserPayload> = when {
  UserDAO.getByUser(user).isEmpty() -> Left(RegisterError.UserDoesNotExist)
  else -> Right(this)
}
