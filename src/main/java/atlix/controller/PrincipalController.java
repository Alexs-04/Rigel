
package atlix.controller;

import atlix.logic.components.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnSales;

    @FXML
    private Button btnShopping;

    @FXML
    private Button btnSuppliers;

    @FXML
    public void initialize() {
        configurarEventos();
    }

    private void configurarEventos() {
        btnProducts.setOnAction(event -> goToProducts());
        btnSales.setOnAction(event -> goToSales());
        btnShopping.setOnAction(event -> goToShopping());
        btnSuppliers.setOnAction(event -> goToSuppliers());
    }

    private void goToProducts() {
        ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Vista", "", "Abriendo vista de Productos...");
        cambiarEscena("/atlix/view/ProductsView.fxml", "Gestión de Productos");
    }

    private void goToSales() {
        ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Vista", "", "Abriendo vista de Ventas...");
        cambiarEscena("/atlix/view/SalesView.fxml", "Gestión de Ventas");
    }

    private void goToShopping() {
        ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Vista", "", "Abriendo vista de Compras...");
        cambiarEscena("/atlix/view/ShoppingView.fxml", "Gestión de Compras");
    }

    private void goToSuppliers() {
        ShowAlert.showAlert(Alert.AlertType.INFORMATION, "Vista", "", "Abriendo vista de Modificaciones...");
        cambiarEscena("/atlix/view/SuppliersView.fxml", "Gestión de Proveedores");
    }

    private void cambiarEscena(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            AnchorPane root = loader.load();
            Stage stage = (Stage) btnProducts.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            ShowAlert.showAlert(Alert.AlertType.ERROR, "Error de navegación", "",
                    "No se pudo cargar la vista: " + rutaFXML);
            e.printStackTrace();
        }
    }
}
