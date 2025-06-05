package atlix.controller;

import atlix.data.Product;
import atlix.logic.services.ProductsService;
import atlix.util.ShowAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ProductsController {

    @FXML
    private TableColumn<Product, String> clnIdMissing;
    @FXML
    private TableColumn<Product, String> clnNameMissing;
    @FXML
    private TableColumn<Product, String> clnDescriptionMissing;
    @FXML
    private TableColumn<Product, String> clnStockMissing;
    @FXML
    private TableColumn<Product, String> clnIdTotal;
    @FXML
    private TableColumn<Product, String> clnNameTotal;
    @FXML
    private TableColumn<Product, String> clnDescriptionTotal;
    @FXML
    private TableColumn<Product, String> clnStockTotal;
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
    private Button btnTotalProducts;
    @FXML
    private Button btnHomeProduct;
    @FXML
    private Button btnSearchProductMissing;
    @FXML
    private Button btnSearchProductTotal;
    @FXML
    private Button btnSaveAddProducts;
    @FXML
    private Button btnSaveModificationProducts;
    @FXML
    private TableView<Product> tblProductTotal;
    @FXML
    private TableView<Product> tblProductsMissing;
    @FXML
    private TextField txtProductBarcode;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductSalePrice;
    @FXML
    private TextField txtProductStock;
    @FXML
    private TextArea txtProductDescription;
    @FXML
    private TextArea txtDescriptionModification;
    @FXML
    private TextField txtSalePriceModification;
    @FXML
    private TextField txtStockModification;
    @FXML
    private TextField txtNameProductModification;
    @FXML
    private TextField txtSearchModification;
    @FXML
    private TextField txtSearchProductMissing;
    @FXML
    private TextField txtSearchProductTotal;
    @FXML
    private TextField txtProductPurchasePrice;
    @FXML
    private ComboBox<String> cbxProductSupplier;
    @FXML
    private ComboBox<String> cbxSupplierModification;
    @FXML
    private AnchorPane viewAddProducts;
    @FXML
    private AnchorPane viewMissingProducts;
    @FXML
    private AnchorPane viewModificationProducts;
    @FXML
    private AnchorPane viewTotalProducts;

    private final ProductsService productsService = new ProductsService();
    private String supplierAux;

    @FXML
    public void initialize() {
        configureEvents();
        txtSearchModification.setOnAction(event -> {
            searchProduct();
            supplierAux = cbxSupplierModification.getValue();
        });
        cbxProductSupplier.setItems(FXCollections.observableArrayList(productsService.getNamesForSuppliers()));
        cbxSupplierModification.setItems(FXCollections.observableArrayList(productsService.getNamesForSuppliers()));

        totalProducts();
    }

    public void configureEvents() {
        btnTotalProducts.setOnAction(event -> {
            goToTotalProducts(); //Cuando se presiona el boton llama a la vista y muestra los productos en la tabla
            totalProducts();
        });
        btnMissingProducts.setOnAction(event -> {
            goToMissingProducts();
            missingProducts();
        });
        btnAddProduct.setOnAction(event -> goToAddProducts());
        btnModificationProduct.setOnAction(event -> goToModificationProducts());
        btnHomeProduct.setOnAction(event -> goToHome());
    }

    public void goToTotalProducts() {
        resetFieldStyles();
        viewTotalProducts.setVisible(true);
        viewMissingProducts.setVisible(false);
        viewAddProducts.setVisible(false);
        viewModificationProducts.setVisible(false);
    }

    public void goToMissingProducts() {
        resetFieldStyles();
        viewTotalProducts.setVisible(false);
        viewMissingProducts.setVisible(true);
        viewAddProducts.setVisible(false);
        viewModificationProducts.setVisible(false);
    }

    public void goToAddProducts() {
        resetFieldStyles();
        viewTotalProducts.setVisible(false);
        viewMissingProducts.setVisible(false);
        viewAddProducts.setVisible(true);
        viewModificationProducts.setVisible(false);
    }

    public void goToModificationProducts() {
        resetFieldStyles();
        viewTotalProducts.setVisible(false);
        viewMissingProducts.setVisible(false);
        viewAddProducts.setVisible(false);
        viewModificationProducts.setVisible(true);

        clearModificationFields();
    }

    public void goToHome() {
        productsService.loadMainView();
        var stage = (Stage) btnHomeProduct.getScene().getWindow();
        productsService.closeWindow(stage);
    }

    public void searchProductTotal() {
        var searchProduct = txtSearchProductTotal.getText().trim();
        if (searchProduct.isEmpty()) {
            txtSearchProductTotal.getStyleClass().add("error-textfield");//cambia el estilo del textfield
            ShowAlert.INSTANCE.showAlert("WARNING", "Campos vacíos", null, "Se debe ingresar un producto a búscar");
            totalProducts();
            return;
        }
        var barCode = Long.parseLong(searchProduct);
        Product product = productsService.searchProductByBarCode(barCode);
        if (product == null) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Producto no encontrado");
            return;

        }
        tblProductTotal.getItems().clear();
        tblProductTotal.getItems().add(product);
        txtSearchProductTotal.clear();
    }

    //Funcionamiento de productos faltantes
    public void searchProductMissing() {
        var searchProduct = txtSearchProductMissing.getText().trim();
        if (!validateRequiredFields(txtSearchProductMissing)) {
            txtSearchProductMissing.getStyleClass().add("error-textfield");
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "Se debe ingresar un producto a buscar");
            return;
        }
        var barCode = Long.parseLong(searchProduct);
        Product product = productsService.searchProductByBarCode(barCode);
        if (product == null) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Sin producto", "", "Producto no encontrado");
            return;
        }
        //Vacíar la tabla y posteriormente agregar el producto encontrado
        tblProductsMissing.getItems().clear();
        tblProductsMissing.getItems().add(product);
        txtSearchProductMissing.clear();
    }

    //Funcionamiento de agregar productos
    public void saveAddProducts() {
        var supplier = cbxProductSupplier.getValue();
        if (!validateRequiredFields(txtProductBarcode, txtProductName, txtProductSalePrice, txtProductPurchasePrice, txtProductStock) || (supplier == null)) {
            cbxProductSupplier.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: #fdecea;");

            ShowAlert.INSTANCE.showAlert("WARNING", "Campos incompletos", "", "Ingrese todos los campos obligatorios");
            return;
        }
        var barcode = Long.parseLong(txtProductBarcode.getText().trim());
        var name = txtProductName.getText().trim();
        var salePrice = Double.parseDouble(txtProductSalePrice.getText().trim());
        var stock = Integer.parseInt(txtProductStock.getText().trim());
        var description = txtProductDescription.getText();
        var purchasePrice = Double.parseDouble(txtProductPurchasePrice.getText().trim());


        Product product = new Product(barcode, name, description, salePrice, stock, 0);

        boolean success = productsService.saveProductWithSupplier(
                product,
                productsService.getIdForSupplier(supplier),
                20
        );

        if (success) {
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Éxito", null, "Producto agregado correctamente.");
            clearAddProductFields();
        } else {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "No se pudo agregar el producto.");
        }
    }

    //Funcionamiento de modificar productos
    public void saveModificationProducts() {
        Product product = null;
        if (txtSearchModification.getText().trim().isEmpty()) {
            txtSearchModification.getStyleClass().add("error-textfield");
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Se debe ingresar un producto a modificar");
            return;
        } else {
            product = productsService.searchProductByBarCode(
                    Long.parseLong(txtSearchModification.getText().trim())
            );
        }
        if (product == null) {
            return;
        }
        var validFields = validateRequiredFields(txtNameProductModification, txtSalePriceModification, txtStockModification);
        var supplier = cbxSupplierModification.getValue();

        boolean supplierEmpty = (supplier == null || supplier.trim().isEmpty());
        if (!validFields && supplierEmpty) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Campos incompletos", "", "Ingrese todos los campos obligatorios");
            cbxSupplierModification.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: #fdecea;");
        }

        if (validFields && !supplierEmpty) {
            var name = txtNameProductModification.getText().trim();
            var salePrice = txtSalePriceModification.getText().trim();
            var stock = txtStockModification.getText().trim();
            var description = txtDescriptionModification.getText().trim();

            boolean flag = !supplier.equals(supplierAux);

            Product updatedProduct = new Product(
                    product.barCode(),
                    name,
                    description,
                    Double.parseDouble(salePrice),
                    Integer.parseInt(stock),
                    product.id()
            );

            if (productsService.updateProduct(
                    updatedProduct,
                    productsService.getIdForSupplier(supplier),
                    productsService.getIdForSupplier(supplierAux),
                    flag
            )) {
                ShowAlert.INSTANCE.showAlert("INFORMATION", "Éxito", null, "Producto modificado correctamente.");
                clearModificationFields();
            }
        }

    }

    private void searchProduct() {
        var searchProduct = txtSearchModification.getText().trim();
        if (validateRequiredFields(txtSearchModification)) {
            txtSearchModification.getStyleClass().remove("error-textfield");

            Product product;
            try {
                product = productsService.searchProductByBarCode(
                        Long.parseLong(searchProduct)
                );

                if (product == null) {
                    ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Producto no encontrado");
                    return;
                }
            } catch (Exception e) {
                ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "El código de barras debe ser un número válido");
                return;
            }

            txtNameProductModification.setText(product.name());
            txtSalePriceModification.setText(String.valueOf(product.price()));
            txtStockModification.setText(String.valueOf(product.stock()));
            txtDescriptionModification.setText(product.description());
            cbxSupplierModification.setValue(
                    productsService.getNameForSupplierByIdProduct(product.id())
            );
        } else {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Se debe ingresar un producto a modificar");
        }

    }

    public void totalProducts() {
        showProducts(tblProductTotal, clnIdTotal, clnNameTotal, clnDescriptionTotal, clnStockTotal, false);
    }

    public void missingProducts() {
        showProducts(tblProductsMissing, clnIdMissing, clnNameMissing, clnDescriptionMissing, clnStockMissing, true);
    }

    //Quita los estilos de error de los campos de texto
    private void resetFieldStyles() {
        txtProductBarcode.getStyleClass().remove("error-textfield");
        txtProductName.getStyleClass().remove("error-textfield");
        txtProductSalePrice.getStyleClass().remove("error-textfield");
        txtProductPurchasePrice.getStyleClass().remove("error-textfield");
        txtProductStock.getStyleClass().remove("error-textfield");
        cbxProductSupplier.setStyle("");
        txtSearchProductMissing.getStyleClass().remove("error-textfield");
        txtSearchProductTotal.getStyleClass().remove("error-textfield");
        txtSearchModification.getStyleClass().remove("error-textfield");
        txtNameProductModification.getStyleClass().remove("error-textfield");
        txtSalePriceModification.getStyleClass().remove("error-textfield");
        txtStockModification.getStyleClass().remove("error-textfield");
        cbxSupplierModification.setStyle("");
    }

    private void showProducts(TableView<Product> table, TableColumn<Product, String> idCol,
                              TableColumn<Product, String> nameCol, TableColumn<Product, String> descCol,
                              TableColumn<Product, String> stockCol, boolean onlyMissing) {

        idCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().id())));
        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().name()));
        descCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().description()));

        stockCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().stock())));

        var products = onlyMissing ? productsService.getMissingProductsStock() : productsService.getAllProducts();
        table.setItems(FXCollections.observableArrayList(products));
    }

    private boolean validateRequiredFields(TextField... fields) {
        boolean valid = true;
        for (TextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                field.getStyleClass().add("error-textfield");
                valid = false;
            }
        }
        return valid;
    }

    private void clearAddProductFields() {
        txtProductBarcode.clear();
        txtProductName.clear();
        txtProductSalePrice.clear();
        txtProductPurchasePrice.clear();
        txtProductStock.clear();
        txtProductDescription.clear();
        cbxSupplierModification.getSelectionModel().clearSelection();
    }

    private void clearModificationFields() {
        txtSearchModification.clear();
        txtNameProductModification.clear();
        txtSalePriceModification.clear();
        txtStockModification.clear();
        txtDescriptionModification.clear();
        cbxSupplierModification.getSelectionModel().clearSelection();
    }
}