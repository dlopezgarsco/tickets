package io.github.dlopezgarsco.services

interface PasswordService {
  fun hash (password: String)
  fun verify (password: String)
}

class PasswordServiceImpl : PasswordService {
  override fun hash(password: String) {
    TODO("Not yet implemented")
  }

  override fun verify(password: String) {
    TODO("Not yet implemented")
  }

}