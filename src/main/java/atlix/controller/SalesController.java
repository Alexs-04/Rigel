package atlix.controller;

import atlix.logic.services.ProductsService;
import atlix.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class SalesController {
    @FXML
    private Button btnSaleClear;

    @FXML
    private Button btnSaleSearch;

    @FXML
    private Button btnSalesHome;

    @FXML
    private Button btnSaleTicket;

    @FXML
    private TableColumn<atlix.model.beans.SaleBean, String> clnSaleBarcodeProduct;

    @FXML
    private TableColumn<atlix.model.beans.SaleBean, String> clnSaleNameProduct;

    @FXML
    private TableColumn<atlix.model.beans.SaleBean, Integer> clnSalePriceProduct;

    @FXML
    private TableColumn<atlix.model.beans.SaleBean, Integer> clnSaleQuantityProduct;

    @FXML
    private Label lblSaleTotal;

    @FXML
    private TableView<atlix.model.beans.SaleBean> tblSaleProducts;

    @FXML
    private TextField txtSearchSaleProduct;

    @FXML
    public void initialize() {
        configureEvents();
    }

    public void configureEvents() {
        btnSalesHome.setOnAction(event -> goToHome());
    }

    public void goToHome() {
        //productService.loadMenuSales();
        // var stage = (Stage) btnHomeProduct.getScene().getWindow();
        // productService.closeWindow(stage);
    }

    public void searchSaleProduct() {
        String search = txtSearchSaleProduct.getText().trim();
        if(search.isEmpty()){
            txtSearchSaleProduct.getStyleClass().add("error-textfield");
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "Se debe ingresar un producto a buscar");
        }
        /*List<Product> productos = ProductsService.searchSaleProduct(search);
        if (productos.isEmpty()) {
            ShowAlert.INSTANCE.showAlert("INFO", "Sin resultados", null, "No se encontró el producto");
            return;
        }else{
         ShowAlert.INSTANCE.showAlert("INFO", "Resultados encontrados", null, "Producto agregado correctamente");
        }
        clnSaleBarcodeProduct.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        clnSaleNameProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnSalePriceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        clnSaleQuantityProduct.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblSaleProducts.setItems(FXCollections.observableArrayList(productos));*/
    }

    public void clearSaleProduct(){
        txtSearchSaleProduct.clear();
        txtSearchSaleProduct.getStyleClass().remove("error-textfield");
        tblSaleProducts.setItems(FXCollections.observableArrayList());
        lblSaleTotal.setText("0");
    }

    public void ticketSale() {
        var productos = tblSaleProducts.getItems();
        if (productos == null || productos.isEmpty()) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "No hay productos para generar el ticket");
            return;
        }
        //(double total = ProductsService.getTotalSale(productos); //Asigna el total de la venta al label
        //lblSaleTotal.setText(String.valueOf(total));

        // Suponiendo que tienes un servicio para generar el ticket
        // SalesService.generateTicket(productos, total);
        ShowAlert.INSTANCE.showAlert("INFORMATION", "Ticket", null, "Ticket generado correctamente");
        clearSaleProduct();
    }

}





