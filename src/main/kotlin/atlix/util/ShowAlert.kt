package atlix.util

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType


object ShowAlert {
    /**
     * Método para cargar alerts
     *
     * @param type   Hace referencia al tipo de alert que se desea
     * @param title  El titulo del alert
     * @param header La cabecera del alert
     * @param text   El texto que mostrara el alert
     */
    fun showAlert(type: AlertType?, title: String?, header: String?, text: String?) {
        val alert = Alert(type)
        alert.title = title
        alert.headerText = header
        alert.contentText = text
        alert.showAndWait()
    }
}
