package atlix.logic.services

import atlix.logic.security.LogIn
import atlix.model.repository.ConsumerRepository

class LoginService {

    fun login(consumerName: String, pass: String?): Boolean {
        val consumerRepository = ConsumerRepository()
        val logIn = LogIn(consumerRepository)

        return logIn.startSession(consumerName, pass)
    }
}