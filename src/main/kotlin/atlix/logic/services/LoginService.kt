package atlix.logic.services

import atlix.logic.security.LogIn
import atlix.model.repository.ConsumerRepository
import atlix.util.Paths
import atlix.util.WindowLoader
import javafx.stage.Stage

class LoginService {

    fun login(consumerName: String, pass: String?): Boolean {
        val consumerRepository = ConsumerRepository()
        val logIn = LogIn(consumerRepository)

        return logIn.startSession(consumerName, pass)
    }

    fun loadMainWindow() {
        WindowLoader().showWindow(Paths.MAIN_VIEW, "Vista Principal", false)
    }

    fun closeWindow(stage: Stage) {
        WindowLoader().closeWindow(stage)
    }
}