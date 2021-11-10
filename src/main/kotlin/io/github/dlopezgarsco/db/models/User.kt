package io.github.dlopezgarsco.db.models

import arrow.core.Either
import arrow.core.Option
import arrow.core.toOption
import io.github.dlopezgarsco.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

object Users : Table() {
  val id = integer("id").autoIncrement()
  val user = varchar("name", 255)
  val password = char("password", 128)
  override val primaryKey = PrimaryKey(id)
}

data class User(
  val id: Int = 0,
  val user: String,
  val password: String = "",
)

object UserDAO : DAO<User> {

  override suspend fun get(id: Int): Option<User> = dbQuery {
    Users.select { (Users.id eq id) }
      .mapNotNull { toPOJO(it) }
      .singleOrNull()
  }.toOption()

  suspend fun getByUser(user: String): Option<User> = dbQuery {
    Users.select { (Users.user eq user) }
      .mapNotNull { toPOJO(it) }
      .singleOrNull()
  }.toOption()

  override suspend fun getAll(): List<User> = dbQuery {
    Users.selectAll().map { toPOJO(it) }
  }

  override suspend fun create(data: User): Either<DAOError, Int> {
    if (getByUser(data.user).isNotEmpty())
      return Either.Left(DAOError.RecordAlreadyExists)
    return Either.Right(dbQuery {
      Users.insert {
        it[user] = data.user
        it[password] = data.password
      }
    } get Users.id)
  }

  override suspend fun update(data: User): Boolean {
    val updatedRows: Int = getByUser(data.user).fold({ throw DAOError.InaccessibleDatabase }, {
      dbQuery {
        Users.update(where = { Users.user eq data.user }) {
          it[password] = data.password
        }
      }
    })
    return updatedRows > 0
  }

  override suspend fun delete(id: Int): Boolean {
    val updatedRows: Int = get(id).fold({ throw DAOError.InaccessibleDatabase }, {
      dbQuery {
        Users.deleteWhere { Users.id eq it.id }
      }
    })
    return updatedRows > 0
  }

  override suspend fun delete(data: User): Boolean {
    val updatedRows: Int = getByUser(data.user).fold({ throw DAOError.InaccessibleDatabase }, {
      dbQuery {
        Users.deleteWhere { Users.id eq it.id }
      }
    })
    return updatedRows > 0
  }

  override suspend fun toPOJO(row: ResultRow): User = User(
    id = row[Users.id],
    user = row[Users.user],
    password = row[Users.password]
  )
}