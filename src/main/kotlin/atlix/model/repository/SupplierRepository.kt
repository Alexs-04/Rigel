package atlix.model.repository

import atlix.model.beans.SupplierBean
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.transaction.Transactional

class SupplierRepository {

    private val entityManager: EntityManager = Persistence.createEntityManagerFactory("prometheus")
        .createEntityManager()

    @Transactional
    fun save(supplierBean: SupplierBean) {
        entityManager.transaction.begin()
        entityManager.persist(supplierBean)
        entityManager.transaction.commit()
    }

    private fun findById(id: Int): SupplierBean? {
        return entityManager.find(SupplierBean::class.java, id)
    }

    fun findAll(): List<SupplierBean> {
        return entityManager.createQuery("SELECT s FROM SupplierBean s", SupplierBean::class.java)
            .resultList
    }

    @Transactional
    fun update(supplierBean: SupplierBean) {
        entityManager.transaction.begin()
        entityManager.merge(supplierBean)
        entityManager.transaction.commit()
    }

    @Transactional
    fun delete(id: Int) {
        entityManager.transaction.begin()
        val supplier = findById(id)
        if (supplier != null) {
            entityManager.remove(supplier)
        }
        entityManager.transaction.commit()
    }
}
