package atlix.app;

import atlix.util.Paths;
import atlix.util.ShowAlert;
import atlix.util.WindowLoader;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

    WindowLoader windowLoader = new WindowLoader();

    @Override
    public void start(Stage primaryStage) {
        try {
            windowLoader.showWindow(Paths.LOGIN, "Inicio de sesión", false);
        } catch (Exception e) {
            ShowAlert.INSTANCE.showAlert(Alert.AlertType.ERROR, "Error", "",
                    "No se pudo cargar la ventana principal");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
