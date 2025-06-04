package atlix.logic.services

import atlix.util.Paths
import atlix.util.WindowLoader
import javafx.fxml.LoadException
import javafx.stage.Stage

class MainService {

    fun loadProductsView() {
        try {
            WindowLoader().showWindow(
                Paths.PRODUCTS_VIEW,
                "Administración de Productos", false
            )
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cargar la vista de productos: ${e.message}")
        }
    }

    fun closeWindow(stage: Stage) {
        try {
            WindowLoader().closeWindow(stage)
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cerrar la ventana: ${e.message}")
        }
    }

    fun loadSalesView() {
        try {
            WindowLoader().showWindow(
                Paths.MENU_SALES_VIEW,
                "Administración de Ventas", false
            )
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cargar la vista de ventas: ${e.message}")
        }
    }

}