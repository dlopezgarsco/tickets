package io.github.dlopezgarsco.utils

import java.util.regex.Pattern

enum class ValidationType {
  EMAIL
}

object Validator {

  fun verify(data: String, type: ValidationType): Boolean {
    return when (type) {
      ValidationType.EMAIL -> verifyEmail(data)
    }
  }

  private fun verifyEmail(data: String): Boolean {
    return Pattern.compile(
      "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
          + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
          + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
          + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
          + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
          + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    )
      .matcher(data)
      .matches()
  }
}