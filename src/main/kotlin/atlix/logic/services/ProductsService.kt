package atlix.logic.services

import atlix.model.repository.ProductRepository

class ProductsService {

    val productRepository: ProductRepository = ProductRepository()

    fun addNewProduct(baCode: String, price: Double, stock: Int, name: String, description: String) {
        val product = atlix.model.beans.ProductBean(
            barCode = baCode.toLong(),
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

    fun getProductsMissingStock(): List<atlix.model.beans.ProductBean> {
        return productRepository.findAll().filter { it.stock <= 0 }
    }


}