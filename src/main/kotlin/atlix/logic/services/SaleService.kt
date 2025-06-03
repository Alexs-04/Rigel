package atlix.logic.services

import atlix.model.repository.SaleRepository
import atlix.util.WindowLoader
import javafx.fxml.LoadException

class SaleService {

    private val saleRepository: SaleRepository = SaleRepository()

    fun showDetailsForSale() {
        try {
            WindowLoader().showWindow("", "", false)
        } catch (e: LoadException) {
            throw RuntimeException("Error while loading sale data", e)
        }
    }


}