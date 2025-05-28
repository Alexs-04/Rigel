package atlix.controller;

import atlix.util.ShowAlert;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class ProductsController {

    @FXML
    private AnchorPane AnchorButtons;
    @FXML
    private AnchorPane AnchorStack;

    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnMissingProducts;
    @FXML
    private Button btnModificationProduct;
    @FXML
    private Button btnSearchProductMissing;
    @FXML
    private Button btnSearchProductTotal;
    @FXML
    private Button btnTotalProducts;

    @FXML
    private TableView<?> tblProductTotal;
    @FXML
    private TableView<?> tblProductsMissing;

    @FXML
    private TextField txtProductBarCode;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductSalePrice;
    @FXML
    private TextField txtProductStock;
    @FXML
    private TextArea txtProductDescription;

    @FXML
    private TextField txtSalePriceModification;
    @FXML
    private TextField txtStockModification;

    @FXML
    private TextField txtSearchProductMissing;
    @FXML
    private TextField txtSearchProductTotal;

    @FXML
    private AnchorPane viewAddProducts;
    @FXML
    private AnchorPane viewMissingProducts;
    @FXML
    private AnchorPane viewModificationProducts;
    @FXML
    private AnchorPane viewTotalProducts;

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
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN)
                txtProductDescription.requestFocus();
            else if (event.getCode() == KeyCode.UP) txtProductSalePrice.requestFocus();
        });

        txtProductDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) btnAddProduct.fire();
            else if (event.getCode() == KeyCode.UP) txtProductStock.requestFocus();
        });
    }

    @FXML
    public void showTotalProducts() {
        setActiveView(viewTotalProducts);
        ShowAlert.INSTANCE.showAlert("INFORMATION", "Vista", "", "Mostrando Productos Totales.");
    }

    @FXML
    public void showMissingProducts() {
        setActiveView(viewMissingProducts);
        ShowAlert.INSTANCE.showAlert("INFORMATION", "Vista", "", "Mostrando Productos Faltantes.");
    }

    @FXML
    public void showAddProducts() {
        setActiveView(viewAddProducts);
        ShowAlert.INSTANCE.showAlert("INFORMATION", "Vista", "", "Mostrando Alta de Productos.");
    }

    @FXML
    public void showModificationProducts() {
        setActiveView(viewModificationProducts);
        ShowAlert.INSTANCE.showAlert("INFORMATION", "Vista", "", "Mostrando Modificación de Productos.");
    }

    private void setActiveView(AnchorPane active) {
        viewAddProducts.setVisible(false);
        viewMissingProducts.setVisible(false);
        viewModificationProducts.setVisible(false);
        viewTotalProducts.setVisible(false);
        active.setVisible(true);
    }

    @FXML
    public void saveProduct() {
        String code = txtProductBarCode.getText().trim();
        String nombre = txtProductName.getText().trim();
        String precioStr = txtProductSalePrice.getText().trim();
        String stockStr = txtProductStock.getText().trim();
        String description = txtProductDescription.getText().trim();

        if (validateEmpty(code, txtProductBarCode) |
                validateEmpty(nombre, txtProductName) |
                validateEmpty(precioStr, txtProductSalePrice) |
                validateEmpty(stockStr, txtProductStock) |
                validateEmpty(description, txtProductDescription)) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Campos incompletos", "", "Por favor completa todos los campos.");
            return;
        }

        try {
            Double.parseDouble(precioStr);
            Integer.parseInt(stockStr);
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Producto guardado", "", "El producto fue registrado correctamente.");
            txtProductBarCode.clear();
            txtProductName.clear();
            txtProductSalePrice.clear();
            txtProductStock.clear();
            txtProductDescription.clear();
        } catch (NumberFormatException e) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Formato inválido", "", "Precio y stock deben ser números.");
        }
    }

    @FXML
    public void modifyProduct() {
        String precio = txtSalePriceModification.getText().trim();
        String stock = txtStockModification.getText().trim();

        if (validateEmpty(precio, txtSalePriceModification) |
                validateEmpty(stock, txtStockModification)) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Campos vacíos", "", "Completa todos los campos de modificación.");
            return;
        }

        try {
            Double.parseDouble(precio);
            Integer.parseInt(stock);
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Modificación exitosa", "", "Producto modificado correctamente.");
            txtSalePriceModification.clear();
            txtStockModification.clear();
        } catch (NumberFormatException e) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error de formato", "", "Verifica el precio y stock ingresados.");
        }
    }

    @FXML
    public void search() {
        String search = txtSearchProductTotal.getText().trim();
        if (search.isEmpty()) {
            txtSearchProductTotal.setStyle("-fx-border-color: crimson; -fx-border-width: 2px;");
            ShowAlert.INSTANCE.showAlert("WARNING", "Campo vacío", "", "Introduce un nombre o código a buscar.");
        } else {
            txtSearchProductTotal.setStyle("");
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Búsqueda", "", "Buscando: " + search);
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
