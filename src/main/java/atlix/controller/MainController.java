package atlix.controller;

import atlix.logic.services.LoadViewService;
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
    private final LoadViewService loadViewService = new LoadViewService();

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
        loadViewService.loadProductsView();
        var stage = (Stage) btnProducts.getScene().getWindow();
        loadViewService.closeWindow(stage);
    }

    private void goToSales() {
        loadViewService.loadMainSalesView();
        var stage = (Stage) btnSales.getScene().getWindow();
        loadViewService.closeWindow(stage);
    }

    private void goToShopping() {
    }

    private void goToSuppliers() {
    }

}
