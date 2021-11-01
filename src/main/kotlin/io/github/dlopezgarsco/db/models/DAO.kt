package io.github.dlopezgarsco.db.models

import arrow.core.Option
import org.jetbrains.exposed.sql.ResultRow

sealed interface DAO<T> {
  suspend fun get(id: Int) : Option<User>
  suspend fun getAll() : List<T>
  suspend fun update(new: T) : Option<User>
  suspend fun delete(id: Int) : Boolean
  suspend fun toPOJO(row: ResultRow): T
}