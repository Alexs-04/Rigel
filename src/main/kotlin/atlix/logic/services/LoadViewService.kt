package atlix.logic.services

import atlix.util.Paths
import atlix.util.WindowLoader
import javafx.fxml.LoadException

class LoadViewService {
    private val loader = WindowLoader()

    fun loadMainSalesView() {
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

    fun loadSalesView() {
        try {
            WindowLoader().showWindow(Paths.SALES_VIEW, "Punto de Venta", false)
        } catch (e: LoadException) {
            throw RuntimeException("Error while loading sales view", e)
        }
    }

    fun loadMainView() {
        try {
            WindowLoader().showWindow(Paths.MAIN_VIEW, "Vista Principal", false)
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cargar la vista principal: ${e.message}")
        }
    }

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

    fun closeWindow(stage: javafx.stage.Stage) {
        try {
            WindowLoader().closeWindow(stage)
        } catch (e: LoadException) {
            e.printStackTrace()
            throw RuntimeException("Error al cerrar la ventana: ${e.message}")
        }
    }
}