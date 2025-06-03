package atlix.logic.services

import atlix.model.repository.SupplierRepository
import atlix.util.WindowLoader
import javafx.fxml.LoadException

class SupplierService {

    private val supplierRepository: SupplierRepository = SupplierRepository()
    private val windowLoader = WindowLoader()

    fun closeWindow(stage: javafx.stage.Stage) = windowLoader.closeWindow(stage)

    fun loadMainView() {
        try {
            WindowLoader().showWindow(atlix.util.Paths.MAIN_VIEW, "Vista Principal", false)
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cargar la vista principal: ${e.message}")
        }
    }

    fun addNewSupplier(name: String, company: String, phone: String, email: String, nameContact: String) {
        val supplier = atlix.model.beans.SupplierBean(
            name = name,
            company = company,
            numberPhone = phone,
            email = email,
            nameContact = nameContact
        )
        supplierRepository.save(supplier)
    }

    fun getProductsForSupplier(supplierId: Int): List<atlix.model.content.ProductSupplier> {
        return supplierRepository.findById(supplierId)?.products?.toList() ?: emptyList()
    }
}