package atlix.logic.services

import atlix.util.Paths
import atlix.util.WindowLoader

class MainService {

    fun loadProductsView() {
        WindowLoader().showWindow(Paths.PRODUCTS_VIEW, "Administración de Productos", false)
    }
}