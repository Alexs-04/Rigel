package atlix.model.repository

import atlix.model.beans.ProductBean
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.transaction.Transactional

class ProductRepository {

    private val entityManager: EntityManager = Persistence
        .createEntityManagerFactory("prometheus")
        .createEntityManager()

    @Transactional
    fun save(product: ProductBean) {
        entityManager.transaction.begin()
        entityManager.persist(product)
        entityManager.transaction.commit()
    }

    private fun findById(barCode: Long): ProductBean? {
        return entityManager.find(ProductBean::class.java, barCode)
    }

    fun findAll(): List<ProductBean> {
        return entityManager.createQuery("SELECT p FROM ProductBean p", ProductBean::class.java)
            .resultList
    }

    fun findBySupplierId(supplierId: Int): List<ProductBean> {
        return entityManager.createQuery(
            "SELECT p FROM ProductBean p WHERE p.supplierBean.id = :supplierId",
            ProductBean::class.java
        )
            .setParameter("supplierId", supplierId)
            .resultList
    }

    @Transactional
    fun update(product: ProductBean) {
        entityManager.transaction.begin()
        entityManager.merge(product)
        entityManager.transaction.commit()
    }

    @Transactional
    fun delete(barCode: Long) {
        entityManager.transaction.begin()
        val product = findById(barCode)
        if (product != null) {
            entityManager.remove(product)
        }
        entityManager.transaction.commit()
    }
}
