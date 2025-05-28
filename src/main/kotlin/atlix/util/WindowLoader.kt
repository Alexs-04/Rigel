package atlix.util

import javafx.fxml.FXMLLoader
import javafx.fxml.LoadException
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage

class WindowLoader {

    /**
     * Método para cargar y mostrar una ventana a partir de un archivo FXML.
     *
     * @param fxmlPath  Ruta del archivo FXML, por ejemplo, "/views/ventana.fxml".
     * @param title     Título de la ventana.
     * @param expansive Indica si la ventana es redimensionable.
     * @throws Exception Si ocurre un error al cargar el archivo FXML.
     */
    @Throws(LoadException::class)
    fun showWindow(fxmlPath: String, title: String, expansive: Boolean) {
        // Cargar el archivo FXML
        val loader = FXMLLoader(javaClass.getResource(fxmlPath))
        val root: Parent = loader.load()

        // Crear una nueva ventana (Stage) y establecer la escena
        val stage = Stage().apply {
            this.title = title
            this.isResizable = expansive
            scene = Scene(root)
            icons.add(Image("/img/icon.png"))
            initModality(Modality.APPLICATION_MODAL) // Hace que la ventana sea modal
        }

        // Mostrar la ventana
        stage.show()
    }

    fun closeWindow(stage: Stage) {
        stage.close()
    }
}
