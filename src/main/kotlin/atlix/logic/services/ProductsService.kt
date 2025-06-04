package atlix.logic.services

import atlix.data.Product
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
        product: Product
    ): ProductBean {
        val product = ProductBean(
            barCode = product.barCode,
            price = product.price,
            stock = product.stock,
            name = product.name,
            description = product.description
        )
        return product
    }

    fun saveProductWithSupplier(
        product: Product,
        supplierId: Int,
        purchasePrice: Double,
    ): Boolean {
        val bean: ProductBean = product(product)
        val supplier = supplierRepository.findById(supplierId)
            ?: throw IllegalArgumentException("Proveedor no encontrado con ID $supplierId")

        // Guardamos el producto primero para obtener su ID
        val savedProduct = productRepository.save(bean)

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
        return true
    }

    fun getNamesForSuppliers(): List<String> {
        return supplierRepository.findAll().map { it.name }
    }

    fun getIdForSupplier(name: String): Int {
        val list = supplierRepository.findAll()

        for (p in list) {
            if (p.name == name) {
                return p.id
            }
        }
        return -1
    }

    fun getNameForSupplierByIdProduct(id: Long): String {
        val productBean = searchBeanById(id)
            ?: throw IllegalArgumentException("Producto no encontrado con ID $id")

        val supplierId = productBean.suppliers.firstOrNull()?.supplier?.id
            ?: throw IllegalArgumentException("No se encontró proveedor para el producto con ID $id")

        return supplierRepository.findById(supplierId)?.name
            ?: throw IllegalArgumentException("Proveedor no encontrado con ID $supplierId")
    }

    fun getAllProducts(): List<Product> {
        return productRepository.findAll().map { productBean ->
            Product(
                productBean.barCode,
                productBean.name,
                productBean.description,
                productBean.price,
                productBean.stock,
                productBean.id
            )
        }
    }

    fun getMissingProductsStock(): List<Product> {
        return productRepository.findAll().filter { it.stock <= 0 }.map { productBean ->
            Product(
                productBean.barCode,
                productBean.name,
                productBean.description,
                productBean.price,
                productBean.stock,
                productBean.id
            )
        }
    }

    fun updateProduct(
        product: Product,
        newSupplierId: Int,
        oldSupplierId: Int,
        flag: Boolean
    ): Boolean {
        val bean = productRepository.findById(product.id) ?: throw IllegalArgumentException("Producto no encontrado con ID ${product.id}")
        bean.name = product.name
        bean.description = product.description
        bean.price = product.price
        bean.stock = product.stock

        if (flag) {
            val purchasePrice = bean?.suppliers?.firstOrNull { it.supplier?.id == oldSupplierId }?.purchasePrice
                ?: throw IllegalArgumentException("No se encontró proveedor con ID $oldSupplierId para el producto")

            // Actualizar el proveedor
            val newSupplier = supplierRepository.findById(newSupplierId)
                ?: throw IllegalArgumentException("Proveedor no encontrado con ID $newSupplierId")

            val oldSupplier = supplierRepository.findById(oldSupplierId)
                ?: throw IllegalArgumentException("Proveedor no encontrado con ID $oldSupplierId")

            val relationToRemove = bean.suppliers.find {
                it.supplier?.id == oldSupplierId
            }

            if (relationToRemove != null) {
                bean.suppliers.remove(relationToRemove)
                oldSupplier.products.remove(relationToRemove)
            }

            val newRelation = ProductSupplier(
                id = ProductSupplierId(product.id, newSupplier.id),
                product = bean,
                supplier = newSupplier,
                purchasePrice = purchasePrice
            )

            bean.suppliers.add(newRelation)
            newSupplier.products.add(newRelation)
            productRepository.update(bean)
            supplierRepository.update(oldSupplier)
            supplierRepository.update(newSupplier)
        }
        productRepository.update(bean)
        return true
    }

    private fun searchBeanByBarCode(barCode: Long): ProductBean? {
        return productRepository.findByBarCode(barCode)
    }

    private fun searchBeanById(id: Long): ProductBean? {
        return productRepository.findById(id)
    }

    fun searchProductById(id: Long): Product? {
        val productBean = searchBeanById(id)
        return productBean?.let {
            Product(
                it.barCode,
                it.name,
                it.description,
                it.price,
                it.stock,
                it.id
            )
        }
    }

    fun searchProductByBarCode(barCode: Long): Product? {
        val productBean = productRepository.findByBarCode(barCode)
        return productBean?.let {
            Product(
                it.barCode,
                it.name,
                it.description,
                it.price,
                it.stock,
                it.id
            )
        }
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