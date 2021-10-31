package db.migration

import io.github.dlopezgarsco.db.models.Users
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class V1__basic_schema: BaseJavaMigration() {
  override fun migrate(context: Context?) {
    users()
  }

  private fun users() {
    transaction {
      SchemaUtils.create(Users)

      Users.insert {
        it[user] = "foo@bar.com"
        it[password] = "hashedpwd"
      }

      Users.insert {
        it[user] = "bar@baz.com"
        it[password] = "hashedpwd"
      }
    }
  }
}