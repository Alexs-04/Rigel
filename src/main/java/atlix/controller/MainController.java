package atlix.controller;

import atlix.logic.services.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnSales;

    @FXML
    private Button btnShopping;

    @FXML
    private Button btnSuppliers;

    private final MainService mainService = new MainService();

    @FXML
    public void initialize() {
        configureEvents();
    }

    private void configureEvents() {
        btnProducts.setOnAction(event -> goToProducts());
        btnSales.setOnAction(event -> goToSales());
        btnShopping.setOnAction(event -> goToShopping());
        btnSuppliers.setOnAction(event -> goToSuppliers());
    }

    private void goToProducts() {
        mainService.loadProductsView();
        var stage = (Stage) btnProducts.getScene().getWindow();
        mainService.closeWindow(stage);
    }

    private void goToSales() {
    }

    private void goToShopping() {
    }

    private void goToSuppliers() {
    }

}
