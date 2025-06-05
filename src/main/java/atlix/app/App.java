package atlix.app;

import atlix.concurrence.Notification;
import atlix.concurrence.NotificationService;
import atlix.util.Paths;
import atlix.util.ShowAlert;
import atlix.util.WindowLoader;
import javafx.application.Application;
import javafx.fxml.LoadException;
import javafx.stage.Stage;

public class App extends Application {

    private final WindowLoader windowLoader = new WindowLoader();

    @Override
    public void start(Stage primaryStage) {
        try {
            windowLoader.showWindow(Paths.MAIN_VIEW, "Inicio de sesión", false);
        } catch (LoadException e) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "",
                    "No se pudo cargar la ventana principal");
        }
    }

    public static void main(String[] args) {
        Thread notificationThread = new Thread(new NotificationService());
        notificationThread.start();
        launch(args);
    }
}
