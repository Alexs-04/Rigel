package atlix.controller;

import atlix.util.Paths;
import atlix.util.WindowLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnSales;

    @FXML
    private Button btnShopping;

    @FXML
    private Button btnSuppliers;

    private final WindowLoader windowLoader = new WindowLoader();

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
        try {
            windowLoader.showWindow(Paths.PRODUCTS_VIEW, "Gestión de Productos", false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void goToSales() {
    }

    private void goToShopping() {
    }

    private void goToSuppliers() {
    }

}
