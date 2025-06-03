package atlix.model.repository

import atlix.model.beans.SaleBean
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.transaction.Transactional

class SaleRepository {

    private val entityManager: EntityManager = Persistence.createEntityManagerFactory("prometheus")
        .createEntityManager()

    @Transactional
    fun save(sale: SaleBean) {
        entityManager.transaction.begin()
        entityManager.persist(sale)
        entityManager.transaction.commit()
    }

    fun findById(id: Int): SaleBean? {
        return entityManager.find(SaleBean::class.java, id)
    }

    fun findAll(): List<SaleBean> {
        return entityManager.createQuery("SELECT s FROM SaleBean s", SaleBean::class.java)
            .resultList
    }

    @Transactional
    fun update(sale: SaleBean) {
        entityManager.transaction.begin()
        entityManager.merge(sale)
        entityManager.transaction.commit()
    }

    @Transactional
    fun delete(id: Int) {
        entityManager.transaction.begin()
        val sale = findById(id)
        if (sale != null) {
            entityManager.remove(sale)
        }
        entityManager.transaction.commit()
    }
}