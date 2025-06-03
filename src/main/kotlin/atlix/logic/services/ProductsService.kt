package atlix.logic.services

import atlix.model.beans.ProductBean
import atlix.model.content.ProductSupplier
import atlix.model.content.ProductSupplierId
import atlix.model.repository.ProductRepository
import atlix.model.repository.SupplierRepository
import atlix.util.WindowLoader
import javafx.fxml.LoadException

class ProductsService {

    private val productRepository: ProductRepository = ProductRepository()
    private val supplierRepository: SupplierRepository = SupplierRepository()

    fun product(
        barCode: String, price: Double, stock: Int,
        name: String, description: String
    ): ProductBean {
        val product = ProductBean(
            barCode = barCode.toLong(),
            price = price,
            stock = stock,
            name = name,
            description = description
        )
        return product
    }

    fun saveProductWithSupplier(
        product: ProductBean,
        supplierId: Int,
        purchasePrice: Double,
        productRepository: ProductRepository,
        supplierRepository: SupplierRepository
    ) {
        val supplier = supplierRepository.findById(supplierId)
            ?: throw IllegalArgumentException("Proveedor no encontrado con ID $supplierId")

        // Guardamos el producto primero para obtener su ID
        val savedProduct = productRepository.save(product)

        val relation = ProductSupplier(
            id = ProductSupplierId(savedProduct.id, supplier.id),
            product = savedProduct,
            supplier = supplier,
            purchasePrice = purchasePrice
        )

        savedProduct.suppliers.add(relation)
        supplier.products.add(relation)

        // Guardamos de nuevo para persistir la relación
        productRepository.update(savedProduct)
        supplierRepository.update(supplier)
    }

    fun getNamesForSuppliers(): List<String> {
        return supplierRepository.findAll().map { it.name }
    }

    fun getAllProducts(): List<ProductBean> {
        return productRepository.findAll()
    }

    fun getMissingProductsStock(): List<ProductBean> {
        return productRepository.findAll().filter { it.stock <= 0 }
    }

    fun updateProduct(
        product: ProductBean,
        newBarCode: String, newPrice: Double, newStock: Int,
        newName: String, newDescription: String
    ) {
        product.barCode = newBarCode.toLong()
        product.price = newPrice
        product.stock = newStock
        product.name = newName
        product.description = newDescription
        productRepository.update(product)
    }

    fun searchProductByBarCode(barCode: String): ProductBean? {
        return productRepository.findByBarCode(barCode.toLong())
    }

    fun loadMainView() {
        try {
            WindowLoader().showWindow(atlix.util.Paths.MAIN_VIEW, "Vista Principal", false)
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cargar la vista principal: ${e.message}")
        }
    }

    fun closeWindow(stage: javafx.stage.Stage) {
        try {
            WindowLoader().closeWindow(stage)
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cerrar la ventana: ${e.message}")
        }
    }

}