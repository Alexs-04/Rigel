package atlix.logic.security

import atlix.model.repository.ConsumerRepository
import org.mindrot.jbcrypt.BCrypt
import java.util.*

class LogIn
    (private val consumerRepository: ConsumerRepository) {

    fun startSession(consumerName: String, pass: String?): Boolean {

        val consumerOpt =
            Optional.ofNullable(consumerRepository.findByConsumerName(consumerName))

        if (consumerOpt.isEmpty) {
            return false
        }

        val consumer = consumerOpt.get()

        return BCrypt.checkpw(pass, consumer.password)
    }
}
