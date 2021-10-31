package io.github.dlopezgarsco.db.models

import org.jetbrains.exposed.sql.Table
import io.github.dlopezgarsco.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object Users : Table() {
  val id = integer("id").autoIncrement()
  val user = varchar("name", 255)
  val password = char("password", 128)
  override val primaryKey = PrimaryKey(id)
}

data class User(
  val id: Int,
  val user: String,
  val password: String
)

object UserDAO : DAO<User> {

  override suspend fun get(id: Int): User? = dbQuery {
    Users.select { (Users.id eq id) }
      .mapNotNull { toPOJO(it) }
      .singleOrNull()
  }

  override suspend fun getAll(): List<User> = dbQuery {
    Users.selectAll().map { toPOJO(it) }
  }

  override suspend fun update(new: User): User? {
    TODO("Not yet implemented")
  }

  override suspend fun delete(id: Int): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun toPOJO(row: ResultRow): User = User(
    id = row[Users.id],
    user = row[Users.user],
    password = row[Users.password]
  )
}