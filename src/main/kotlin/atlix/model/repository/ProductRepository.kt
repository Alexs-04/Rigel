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
    fun save(product: ProductBean): ProductBean {
        entityManager.transaction.begin()
        entityManager.persist(product)
        entityManager.transaction.commit()
        return product
    }


    fun findById(id: Long): ProductBean? {
        return entityManager.find(ProductBean::class.java, id)
    }

    fun findByBarCode(barCode: Long): ProductBean? {
        return entityManager.createQuery(
            "SELECT p FROM ProductBean p WHERE p.barCode = :barCode",
            ProductBean::class.java
        )
            .setParameter("barCode", barCode)
            .resultList
            .firstOrNull()
    }

    fun findAll(): List<ProductBean> {
        return entityManager.createQuery("SELECT p FROM ProductBean p", ProductBean::class.java)
            .resultList
    }

    @Transactional
    fun update(product: ProductBean?) {
        entityManager.transaction.begin()
        entityManager.merge(product)
        entityManager.transaction.commit()
    }

    @Transactional
    fun delete(id: Long) {
        entityManager.transaction.begin()
        val product = findById(id)
        if (product != null) {
            entityManager.remove(product)
        }
        entityManager.transaction.commit()
    }
}
