package io.github.dlopezgarsco.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import javax.sql.DataSource

fun configureDatabase() {
  DatabaseFactory.start()
}

object DatabaseFactory {

  fun start() {
    val pool = hikari()
    Database.connect(pool)
    runFlyway(pool)
  }

  private fun hikari(): HikariDataSource {
    val config = HikariConfig().apply {
      driverClassName = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:mem:test"
      maximumPoolSize = 3
      isAutoCommit = false
      transactionIsolation = "TRANSACTION_REPEATABLE_READ"
      validate()
    }
    return HikariDataSource(config)
  }

  private fun runFlyway(datasource: DataSource) {
    val flyway = Flyway.configure()
      .dataSource(datasource)
      .load()
    try {
      flyway.info()
      flyway.migrate()
    } catch (e: Exception) {
      throw e
    }
  }

  suspend fun <T> dbQuery(
    block: suspend () -> T
  ): T =
    newSuspendedTransaction { block() }

}