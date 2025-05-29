package atlix.logic.services

import atlix.model.repository.ProductRepository

class ProductsService {

    val productRepository: ProductRepository = ProductRepository()

    fun addNewProduct(barCode: String, price: Double, stock: Int, name: String, description: String) {
        val product = atlix.model.beans.ProductBean(
            barCode = barCode.toLong(),
            price = price,
            stock = stock,
            name = name,
            description = description
        )
        productRepository.save(product)
    }

    fun getAllProducts(): List<atlix.model.beans.ProductBean> {
        return productRepository.findAll()
    }

    fun getMissingProductsStock(): List<atlix.model.beans.ProductBean> {
        return productRepository.findAll().filter { it.stock <= 0 }
    }

}