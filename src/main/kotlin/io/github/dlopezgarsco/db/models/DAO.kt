package io.github.dlopezgarsco.db.models

import arrow.core.Either
import arrow.core.Option
import org.jetbrains.exposed.sql.ResultRow

sealed interface DAO<T> {
  suspend fun get(id: Int) : Option<T>
  suspend fun getAll() : List<T>
  suspend fun create(data: T) : Either<DAOError, Number>
  suspend fun update(data: T) : Boolean
  suspend fun delete(id: Int) : Boolean
  suspend fun delete(data: T) : Boolean
  suspend fun toPOJO(row: ResultRow): T
}

sealed class DAOError {

  object InaccessibleDatabase : Exception()
  object RecordDoesntExist : DAOError()
  object RecordAlreadyExists: DAOError()

}