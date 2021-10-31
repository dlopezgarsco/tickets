package io.github.dlopezgarsco.db.models

import org.jetbrains.exposed.sql.ResultRow

sealed interface DAO<T> {
  suspend fun get(id: Int) : T?
  suspend fun getAll() : List<T>
  suspend fun update(new: T) : T?
  suspend fun delete(id: Int) : Boolean
  suspend fun toPOJO(row: ResultRow): T
}