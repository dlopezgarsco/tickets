package io.github.dlopezgarsco.db.models

import arrow.core.Either
import arrow.core.Option
import org.jetbrains.exposed.sql.ResultRow

sealed interface DAO<T> {
  suspend fun get(id: Int) : Option<T>
  suspend fun getAll() : List<T>
  suspend fun create(new: T) : Either<DAOError, Number>
  suspend fun update(new: T) : Option<T>
  suspend fun delete(id: Int) : Boolean
  suspend fun toPOJO(row: ResultRow): T
}

sealed class DAOError {

  object RecordDoesntExist : DAOError()
  object RecordAlreadyExists: DAOError()

}