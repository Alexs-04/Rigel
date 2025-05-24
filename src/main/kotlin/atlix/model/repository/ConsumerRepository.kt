package atlix.model.repository

import atlix.model.beans.ConsumerBean
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.transaction.Transactional

class ConsumerRepository {

    private val entityManager: EntityManager = Persistence.createEntityManagerFactory("prometheus")
        .createEntityManager()

    @Transactional
    fun save(consumer: ConsumerBean) {
        entityManager.transaction.begin()
        entityManager.persist(consumer)
        entityManager.transaction.commit()
    }

    fun findById(id: Int): ConsumerBean? {
        return entityManager.find(ConsumerBean::class.java, id)
    }

    fun findAll(): List<ConsumerBean> {
        return entityManager.createQuery("SELECT c FROM ConsumerBean c", ConsumerBean::class.java)
            .resultList
    }

    fun findByConsumerName(consumerName: String): ConsumerBean? {
        return entityManager.createQuery(
            "SELECT c FROM ConsumerBean c WHERE c.consumerName = :consumerName",
            ConsumerBean::class.java
        )
            .setParameter("consumerName", consumerName)
            .resultList
            .firstOrNull()
    }

    @Transactional
    fun update(consumer: ConsumerBean) {
        entityManager.transaction.begin()
        entityManager.merge(consumer)
        entityManager.transaction.commit()
    }

    @Transactional
    fun delete(id: Int) {
        entityManager.transaction.begin()
        val consumer = findById(id)
        if (consumer != null) {
            entityManager.remove(consumer)
        }
        entityManager.transaction.commit()
    }
}
