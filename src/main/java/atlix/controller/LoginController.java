package atlix.controller;

import atlix.logic.services.LoginService;
import atlix.util.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    public VBox btnStart;
    @FXML
    public TextField txfUser;
    @FXML
    public PasswordField txpPassword;

    private final LoginService loginService = new LoginService();

    public void login() {
        String username = txfUser.getText();
        String password = txpPassword.getText();

        errorStyle();

        if (username.isEmpty() || password.isEmpty()) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Campos vacíos",
                    "", "Por favor, complete todos los campos antes de continuar.");
            return;
        }

        if (loginService.login(username, password)) {
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Inicio de sesión exitoso",
                    "", "Bienvenido, " + username + "!");
            loginService.loadMainWindow();
            var stage = (Stage) btnStart.getScene().getWindow();
            loginService.closeWindow(stage);
        } else {
            ShowAlert.INSTANCE.showAlert("ERROR", "Inicio de sesión fallido",
                    "", "Usuario o contraseña incorrectos. Inténtelo de nuevo.");
            txfUser.clear();
            txpPassword.clear();
            errorStyle();
        }
    }

    private void errorStyle() {
        if (txfUser.getText().isEmpty()) {
            txfUser.setStyle("-fx-border-color: crimson; -fx-border-width: 2px;");
        } else {
            txfUser.setStyle("");
        }
        if (txpPassword.getText().isEmpty()) {
            txpPassword.setStyle("-fx-border-color: crimson; -fx-border-width: 2px;");
        } else {
            txpPassword.setStyle("");
        }
    }

    @FXML
    public void initialize() {
        txpPassword.setOnAction(event -> login());
    }
}
