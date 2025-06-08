package atlix.controller;

import atlix.logic.services.LoadViewService;
import atlix.logic.services.ProductsService;
import atlix.logic.services.SaleService;
import atlix.model.beans.ProductBean;
import atlix.model.beans.SaleBean;
import atlix.model.content.SaleDescription;
import atlix.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private TableColumn<SaleTableItem, String> clnSaleBarcodeProduct;
    @FXML
    private TableColumn<SaleTableItem, String> clnSaleNameProduct;
    @FXML
    private TableColumn<SaleTableItem, Double> clnSalePriceProduct;
    @FXML
    private TableColumn<SaleTableItem, Integer> clnSaleQuantityProduct;
    @FXML
    private Label lblSaleTotal;
    @FXML
    private TableView<SaleTableItem> tblSaleProducts;
    @FXML
    private TextField txtSearchSaleProduct;

    private final LoadViewService loadViewService = new LoadViewService();
    private final ProductsService productsService = new ProductsService();
    private final SaleService saleService = new SaleService();

    private final ObservableList<SaleTableItem> saleProducts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configureTable();
        configureEvents();
        txtSearchSaleProduct.setOnAction(event -> {
            searchSaleProduct();
        });
    }

    private void configureTable() {
        clnSaleBarcodeProduct.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        clnSaleNameProduct.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        clnSalePriceProduct.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        clnSaleQuantityProduct.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        tblSaleProducts.setItems(saleProducts);
    }

    public void configureEvents() {
        btnSalesHome.setOnAction(event -> goToHome());
        btnSaleSearch.setOnAction(event -> searchSaleProduct());
        btnSaleClear.setOnAction(event -> clearSaleProduct());
        btnSaleTicket.setOnAction(event -> ticketSale());
        txtSearchSaleProduct.setOnAction(event -> searchSaleProduct());
    }

    public void goToHome() {
        loadViewService.loadMainSalesView();
    }

    public void searchSaleProduct() {
        String search = txtSearchSaleProduct.getText().trim();
        if (search.isEmpty()) {
            txtSearchSaleProduct.getStyleClass().add("error-textfield");
            ShowAlert.INSTANCE.showAlert("WARNING", "Error", null, "Se debe ingresar un producto a buscar");
            return;
        }
        txtSearchSaleProduct.setText("");
        txtSearchSaleProduct.getStyleClass().remove("error-textfield");

        long barcode;
        try {
            barcode = Long.parseLong(search);
        } catch (NumberFormatException e) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", null, "El código de barras debe ser numérico");
            return;
        }

        var product = productsService.searchProductByBarCode(barcode);
        if (product == null) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Producto inexistente", null, "No se encontró el producto");
            return;
        }

        if (product.stock() <= 0) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Producto sin stock", null, "El producto no tiene stock disponible");
            return;
        }

        // Si ya está en la tabla, incrementa cantidad
        for (SaleTableItem item : saleProducts) {
            if (item.getBarcode().equals(String.valueOf(product.barCode()))) {
                item.setQuantity(item.getQuantity() + 1);
                tblSaleProducts.refresh();
                updateTotal();
                return;
            }
        }
        // Si no está, lo agrega
        saleProducts.add(new SaleTableItem(
                String.valueOf(product.barCode()),
                product.name(),
                product.price(),
                1,
                product.id()
        ));
        updateTotal();
    }

    public void clearSaleProduct() {
        txtSearchSaleProduct.clear();
        txtSearchSaleProduct.getStyleClass().remove("error-textfield");
        lblSaleTotal.setText("0");
        if (saleProducts.isEmpty()) {
            ShowAlert.INSTANCE.showAlert("WARNING", "No hay productos", null, "No hay productos para limpiar");
            return;
        } else {
            ShowAlert.INSTANCE.showAlert("INFORMATION", "Productos limpiados", null, "Se han limpiado los productos de la venta");
        }
        saleProducts.clear();
    }


    public void ticketSale() {
        if (saleProducts.isEmpty()) {
            ShowAlert.INSTANCE.showAlert("WARNING", "Sin productos", null, "No hay productos para generar el ticket");
            return;
        }

        double total = saleProducts.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        lblSaleTotal.setText(String.valueOf(total));

        List<SaleDescription> detalles = new ArrayList<>();
        SaleBean saleBean = new SaleBean(0L, LocalDate.now(), total, new ArrayList<>());
        for (SaleTableItem item : saleProducts) {
            ProductBean productBean = new ProductBean();
            productBean.setId(item.getProductId());
            // Actualizar stock del producto
            productsService.updateStock(Long.parseLong(item.getBarcode()), item.getQuantity());
            SaleDescription desc = new SaleDescription(
                    0L,
                    saleBean,
                    productBean,
                    item.getQuantity(),
                    item.getPrice(),
                    item.getPrice() * item.getQuantity()
            );
            detalles.add(desc);
        }

        saleService.saveSaleWithDescriptions(LocalDate.now(), total, detalles);

        ShowAlert.INSTANCE.showAlert("INFORMATION", "Ticket", null, "Ticket generado correctamente");
        clearSaleProduct();
    }

    private void updateTotal() {
        double total = saleProducts.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        lblSaleTotal.setText(String.valueOf(total));
    }

    // Clase interna para la tabla
    public static class SaleTableItem {
        private final javafx.beans.property.SimpleStringProperty barcode;
        private final javafx.beans.property.SimpleStringProperty name;
        private final javafx.beans.property.SimpleDoubleProperty price;
        private final javafx.beans.property.SimpleIntegerProperty quantity;
        private final long productId;

        public SaleTableItem(String barcode, String name, double price, int quantity, long productId) {
            this.barcode = new javafx.beans.property.SimpleStringProperty(barcode);
            this.name = new javafx.beans.property.SimpleStringProperty(name);
            this.price = new javafx.beans.property.SimpleDoubleProperty(price);
            this.quantity = new javafx.beans.property.SimpleIntegerProperty(quantity);
            this.productId = productId;
        }

        public String getBarcode() {
            return barcode.get();
        }

        public String getName() {
            return name.get();
        }

        public double getPrice() {
            return price.get();
        }

        public int getQuantity() {
            return quantity.get();
        }

        public long getProductId() {
            return productId;
        }

        public void setQuantity(int value) {
            quantity.set(value);
        }

        public javafx.beans.property.SimpleStringProperty barcodeProperty() {
            return barcode;
        }

        public javafx.beans.property.SimpleStringProperty nameProperty() {
            return name;
        }

        public javafx.beans.property.SimpleDoubleProperty priceProperty() {
            return price;
        }

        public javafx.beans.property.SimpleIntegerProperty quantityProperty() {
            return quantity;
        }
    }
}