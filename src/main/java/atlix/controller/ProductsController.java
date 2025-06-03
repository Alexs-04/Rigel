package atlix.controller;

import atlix.logic.services.ProductsService;
import atlix.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class ProductsController {

    @FXML
    public TableColumn<atlix.model.beans.ProductBean, Long> clnIdMissing;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, String> clnNameMissing;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, String> clnDescriptionMissing;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, Integer> clnStockMissing;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, Long> clnIdTotal;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, Integer> clnNameTotal;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, String> clnDescriptionTotal;
    @FXML
    public TableColumn<atlix.model.beans.ProductBean, Integer> clnStockTotal;
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
    private TableView<atlix.model.beans.ProductBean> tblProductTotal;
    @FXML
    private TableView<atlix.model.beans.ProductBean> tblProductsMissing;
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

    //private final ProductsService productsService = new ProductsService();

    @FXML
    public void initialize() {
        configureEvents();
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

        txtProductBarcode.clear();
        txtProductName.clear();
        txtProductSalePrice.clear();
        txtProductStock.clear();
        txtProductDescription.clear();
    }

    public void goToModificationProducts() {
        resetFieldStyles();
        viewTotalProducts.setVisible(false);
        viewMissingProducts.setVisible(false);
        viewAddProducts.setVisible(false);
        viewModificationProducts.setVisible(true);

        txtSearchModification.clear();
        txtNameProductModification.clear();
        txtSalePriceModification.clear();
        txtStockModification.clear();
        txtDescriptionModification.clear();
        cbxSupplierModification.getSelectionModel().clearSelection();
    }

    public void goToHome() {
        //productService.loadMainView();
        // var stage = (Stage) btnHomeProduct.getScene().getWindow();
        // productService.closeWindow(stage);
    }

    //Funcionamiento de produstos totales
    public void totalProducts() {
        clnIdTotal.setCellValueFactory(new PropertyValueFactory<>("id"));
        clnNameTotal.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnDescriptionTotal.setCellValueFactory(new PropertyValueFactory<>("description"));
        clnStockTotal.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //tblProductTotal.setItems(FXCollections.observableArrayList(productsService.getAllProducts()));
    }

    public void searchProductTotal() {
        var searchProduct = txtSearchProductTotal.getText().trim();
        if (searchProduct.isEmpty()) {
            txtSearchProductTotal.getStyleClass().add("error-textfield");//cambia el estilo del textfield
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "Se debe ingresar un producto a busar");
            return;
        }
        /*var products = productsService.searchProductTotal(searchProduct);
        if(products.isEmpty()){
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Producto no encontrado");
        }*/
    }

    //Funcionamiento de productos faltantes
    public void missingProducts() {
        clnIdMissing.setCellValueFactory(new PropertyValueFactory<>("id"));
        clnNameMissing.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnDescriptionMissing.setCellValueFactory(new PropertyValueFactory<>("description"));
        clnStockMissing.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //tblProductsMissing.setItems(FXCollections.observableArrayList(productsService.getAllProducts()));
    }

    public void searchProductMissing() {
        var searchProduct = txtSearchProductMissing.getText().trim();
        if (searchProduct.isEmpty()) {
            txtSearchProductMissing.getStyleClass().add("error-textfield");
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "Se debe ingresar un producto a busar");
            return;
        }
        /*var products = productsService.searchProductMissing(searchProduct);
        if(products.isEmpty()){
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Producto faltante no encontrado");
        }*/
    }

    //Funcionamiento de agregar productos
    public void saveAddProducts() {
        var barcode = txtProductBarcode.getText().trim();
        var name = txtProductName.getText().trim();
        var salePrice = txtProductSalePrice.getText().trim();
        var stock = txtProductStock.getText().trim();
        var supplier = cbxProductSupplier.getValue();
        var description = txtProductDescription.getText();

        if (barcode.isEmpty() || name.isEmpty() || salePrice.isEmpty() || stock.isEmpty() || supplier == null) {
            txtProductBarcode.getStyleClass().add("error-textfield");
            txtProductName.getStyleClass().add("error-textfield");
            txtProductSalePrice.getStyleClass().add("error-textfield");
            txtProductStock.getStyleClass().add("error-textfield");
            cbxProductSupplier.setStyle(
                    "-fx-border-color: red;"
                            + "    -fx-border-width: 2px;"
                            + "    -fx-background-color: #fdecea;");
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Ingrese todos los campos obligatorios");
            return;
        }
        /*atlix.model.beans.ProductBean product = new atlix.model.beans.ProductBean(
                barcode, name, Double.parseDouble(salePrice), Integer.parseInt(stock), supplier, description
        );

        boolean success = productsService.addProduct(product);
        if (success) {
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Éxito", null, "Producto agregado correctamente.");
            txtProductBarcode.clear();
            txtProductName.clear();
            txtProductSalePrice.clear();
            txtProductStock.clear();
            txtProductDescription.clear();
        } else {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "No se pudo agregar el producto.");
        }*/
    }

    //Funcionamiento de modificar productos
    public void saveModificationProducts() {
        var searchProduct = txtSearchModification.getText().trim();
        var name = txtNameProductModification.getText().trim();
        var salePrice = txtSalePriceModification.getText().trim();
        var stock = txtStockModification.getText().trim();
        var supplier = cbxSupplierModification.getValue();
        var description = txtDescriptionModification.getText().trim();

        if (searchProduct.isEmpty()) {
            txtSearchModification.getStyleClass().add("error-textfield");
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Se debe ingresar un producto a modificar");
        } else if (name.isEmpty() || salePrice.isEmpty() || stock.isEmpty() || supplier.isEmpty()) {
            txtSearchModification.getStyleClass().remove("error-textfield");
            txtNameProductModification.getStyleClass().add("error-textfield");
            txtSalePriceModification.getStyleClass().add("error-textfield");
            txtStockModification.getStyleClass().add("error-textfield");
            cbxSupplierModification.setStyle(
                    " -fx-border-color: red;"
                            + "    -fx-border-width: 2px;"
                            + "    -fx-background-color: #fdecea;");

            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "Ingrese todos los campos obligatorios");
            return;
        }
        /*atlix.model.beans.ProductBean product = new atlix.model.beans.ProductBean(
                name, Double.parseDouble(salePrice), Integer.parseInt(stock), supplier, description
        );

        boolean success = productsService.modifyProduct(product);
        if (success) {
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Éxito", null, "Producto modificado correctamente.");
            txtNameProductModification.clear();
            txtSalePriceModification.clear();
            txtStockModification.clear();
            txtDescriptionModification.clear();
        } else {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "No se pudo modificar el producto.");
        }*/
    }

    //Quita los estilos de error de los campos de texto
    private void resetFieldStyles() {
        txtProductBarcode.getStyleClass().remove("error-textfield");
        txtProductName.getStyleClass().remove("error-textfield");
        txtProductSalePrice.getStyleClass().remove("error-textfield");
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
}