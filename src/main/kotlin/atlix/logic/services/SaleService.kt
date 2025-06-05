package atlix.logic.services

import atlix.data.Sale
import atlix.model.beans.SaleBean
import atlix.model.repository.SaleRepository
import atlix.util.Paths
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

    fun loadSalesView() {
        try {
            WindowLoader().showWindow(Paths.SALES_VIEW, "Punto de Venta", false)
        } catch (e: LoadException) {
            throw RuntimeException("Error while loading sales view", e)
        }
    }


}