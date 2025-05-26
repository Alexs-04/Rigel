
package atlix.controller;

import atlix.logic.components.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class ProductsController {

    @FXML private AnchorPane AnchorButtons;
    @FXML private AnchorPane AnchorStack;

    @FXML private Button btnAddProduct;
    @FXML private Button btnMissingProducts;
    @FXML private Button btnModificationProduct;
    @FXML private Button btnSeachProductMissing;
    @FXML private Button btnSeachProductTotal;
    @FXML private Button btnTotalProducts;

    @FXML private TableView<?> tblProductTotal;
    @FXML private TableView<?> tblProductsMissing;

    @FXML private TextField txtBarCodeModification;
    @FXML private TextField txtProductBarCode;
    @FXML private TextArea txtProductDescription;
    @FXML private TextField txtProductName;
    @FXML private TextField txtProductSalePrice;
    @FXML private TextField txtProductStock;
    @FXML private TextField txtSalePriceModification;
    @FXML private TextField txtSeachProductMissing;
    @FXML private TextField txtSeachProductTotal;
    @FXML private TextField txtStockModification;

    @FXML private AnchorPane viewAddProducts;
    @FXML private AnchorPane viewMissingProducts;
    @FXML private AnchorPane viewModificationProducts;
    @FXML private AnchorPane viewTotalProducts;

    @FXML
    public void initialize() {
        showAddProducts();
        setupKeyboardEvents();
    }

    private void setupKeyboardEvents() {
        txtProductBarCode.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) txtProductName.requestFocus();
        });

        txtProductName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) txtProductSalePrice.requestFocus();
            else if (event.getCode() == KeyCode.UP) txtProductBarCode.requestFocus();
        });

        txtProductSalePrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) txtProductStock.requestFocus();
            else if (event.getCode() == KeyCode.UP) txtProductName.requestFocus();
        });

        txtProductStock.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) txtProductDescription.requestFocus();
            else if (event.getCode() == KeyCode.UP) txtProductSalePrice.requestFocus();
        });

        txtProductDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) btnAddProduct.fire();
            else if (event.getCode() == KeyCode.UP) txtProductStock.requestFocus();
        });
    }

    @FXML public void showTotalProducts() {
        setActiveView(viewTotalProducts);
        ShowAlert.showAlert(AlertType.INFORMATION, "Vista", "", "Mostrando Productos Totales.");
    }

    @FXML public void showMissingProducts() {
        setActiveView(viewMissingProducts);
        ShowAlert.showAlert(AlertType.INFORMATION, "Vista", "", "Mostrando Productos Faltantes.");
    }

    @FXML public void showAddProducts() {
        setActiveView(viewAddProducts);
        ShowAlert.showAlert(AlertType.INFORMATION, "Vista", "", "Mostrando Alta de Productos.");
    }

    @FXML public void showModificationProducts() {
        setActiveView(viewModificationProducts);
        ShowAlert.showAlert(AlertType.INFORMATION, "Vista", "", "Mostrando Modificación de Productos.");
    }

    private void setActiveView(AnchorPane active) {
        viewAddProducts.setVisible(false);
        viewMissingProducts.setVisible(false);
        viewModificationProducts.setVisible(false);
        viewTotalProducts.setVisible(false);
        active.setVisible(true);
    }

    @FXML public void saveProduct() {
        String codigo = txtProductBarCode.getText().trim();
        String nombre = txtProductName.getText().trim();
        String precioStr = txtProductSalePrice.getText().trim();
        String stockStr = txtProductStock.getText().trim();
        String descripcion = txtProductDescription.getText().trim();

        if (validateEmpty(codigo, txtProductBarCode) |
            validateEmpty(nombre, txtProductName) |
            validateEmpty(precioStr, txtProductSalePrice) |
            validateEmpty(stockStr, txtProductStock) |
            validateEmpty(descripcion, txtProductDescription)) {
            ShowAlert.showAlert(AlertType.WARNING, "Campos incompletos", "", "Por favor completa todos los campos.");
            return;
        }

        try {
            Double.parseDouble(precioStr);
            Integer.parseInt(stockStr);
            ShowAlert.showAlert(AlertType.INFORMATION, "Producto guardado", "", "El producto fue registrado correctamente.");
            txtProductBarCode.clear();
            txtProductName.clear();
            txtProductSalePrice.clear();
            txtProductStock.clear();
            txtProductDescription.clear();
        } catch (NumberFormatException e) {
            ShowAlert.showAlert(AlertType.ERROR, "Formato inválido", "", "Precio y stock deben ser números.");
        }
    }

    @FXML public void modifyProduct() {
        String codigo = txtBarCodeModification.getText().trim();
        String precio = txtSalePriceModification.getText().trim();
        String stock = txtStockModification.getText().trim();

        if (validateEmpty(codigo, txtBarCodeModification) |
            validateEmpty(precio, txtSalePriceModification) |
            validateEmpty(stock, txtStockModification)) {
            ShowAlert.showAlert(AlertType.WARNING, "Campos vacíos", "", "Completa todos los campos de modificación.");
            return;
        }

        try {
            Double.parseDouble(precio);
            Integer.parseInt(stock);
            ShowAlert.showAlert(AlertType.INFORMATION, "Modificación exitosa", "", "Producto modificado correctamente.");
            txtBarCodeModification.clear();
            txtSalePriceModification.clear();
            txtStockModification.clear();
        } catch (NumberFormatException e) {
            ShowAlert.showAlert(AlertType.ERROR, "Error de formato", "", "Verifica el precio y stock ingresados.");
        }
    }

    @FXML public void searchTotalProducts() {
        String busqueda = txtSeachProductTotal.getText().trim();
        if (busqueda.isEmpty()) {
            txtSeachProductTotal.setStyle("-fx-border-color: crimson; -fx-border-width: 2px;");
            ShowAlert.showAlert(AlertType.WARNING, "Campo vacío", "", "Introduce un nombre o código a buscar.");
        } else {
            txtSeachProductTotal.setStyle("");
            ShowAlert.showAlert(AlertType.INFORMATION, "Búsqueda", "", "Buscando: " + busqueda);
        }
    }

    @FXML public void searchMissingProducts() {
        String busqueda = txtSeachProductMissing.getText().trim();
        if (busqueda.isEmpty()) {
            txtSeachProductMissing.setStyle("-fx-border-color: crimson; -fx-border-width: 2px;");
            ShowAlert.showAlert(AlertType.WARNING, "Campo vacío", "", "Introduce un nombre o código a buscar.");
        } else {
            txtSeachProductMissing.setStyle("");
            ShowAlert.showAlert(AlertType.INFORMATION, "Búsqueda", "", "Buscando: " + busqueda);
        }
    }

    private boolean validateEmpty(String value, TextInputControl field) {
        if (value.isEmpty()) {
            field.setStyle("-fx-border-color: crimson; -fx-border-width: 2px;");
            return true;
        } else {
            field.setStyle("");
            return false;
        }
    }
}
