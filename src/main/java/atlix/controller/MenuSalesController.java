package atlix.controller;

import atlix.logic.services.ProductsService;
import atlix.logic.services.SaleService;
import atlix.util.ShowAlert;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuSalesController {
    @FXML
    private AnchorPane AnchorButtons;

    @FXML
    private AnchorPane AnchorStack;

    @FXML
    private Button btnMenuEarnings;

    @FXML
    private Button btnMenuHome;

    @FXML
    private Button btnMenuRecord;

    @FXML
    private Button btnMenuSale;

    @FXML
    private TableColumn<atlix.model.beans.SaleBean, String> clnNameEarning;
    @FXML
    private TableColumn<atlix.model.beans.SaleBean, String> clnPriceEarning;
    @FXML
    private TableColumn<atlix.model.beans.SaleBean, Integer> clnAmountEarning;
    @FXML
    private TableColumn<atlix.model.beans.SaleBean, Integer> clnIdRecord;
    @FXML
    private TableColumn<atlix.model.beans.SaleBean, String> clnDateRecord;
    @FXML
    private TableColumn<atlix.model.beans.SaleBean, String> clnTotalRecord;
    @FXML
    private TableColumn<atlix.model.beans.SaleBean, Integer> clnAmountRecord;
    @FXML
    private TableView<atlix.model.beans.SaleBean> tblSaleEarning;
    @FXML
    private TableView<atlix.model.beans.SaleBean> tblSaleRecord;
    @FXML
    private ComboBox<String> cbxEarning;

    @FXML
    private AnchorPane viewEarnings;

    @FXML
    private AnchorPane viewRecords;

    private final SaleService saleService = new SaleService();

    public void initialize() {

    }

    public void configureEvents() {
        btnMenuHome.setOnAction(event -> goToHome());
        btnMenuSale.setOnAction(event -> goToSale());
        btnMenuRecord.setOnAction(event -> goToRecord());
        btnMenuEarnings.setOnAction(event -> goToEarnings());
    }

    public void goToSale() {
        //SaleService.loadSalesView();
        // var stage = (Stage) btnMenuSale.getScene().getWindow();
        // productService.closeWindow(stage);
    }

    public void goToRecord() {
        viewRecords.setVisible(true);
        viewEarnings.setVisible(false);
    }

    public void goToEarnings() {
        viewRecords.setVisible(false);
        viewEarnings.setVisible(true);
    }

    public void goToHome() {
        //SaleService.loadMenuHome();
        var stage = (Stage) btnMenuHome.getScene().getWindow();
        //SaleService.closeWindow(stage);
    }

    public void earningTable() {
        clnNameEarning.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnPriceEarning.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        clnAmountEarning.setCellValueFactory(new PropertyValueFactory<>("amountSold"));


        /*cbxEarning.setItems(FXCollections.observableArrayList(SalesService.getCategories()));

        // Evento de selección
        cbxEarning.setOnAction(event -> {
            String selected = cbxEarning.getValue();
            var products = productsService.getProductsByCategory(selected);
            tblSaleEarning.setItems(FXCollections.observableArrayList(products));*/
    }

    public void recordTable() {
        clnIdRecord.setCellValueFactory(new PropertyValueFactory<>("id"));
        clnDateRecord.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        clnTotalRecord.setCellValueFactory(new PropertyValueFactory<>("total"));
        clnAmountRecord.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tblSaleRecord.setRowFactory(tv -> {
            TableRow<atlix.model.beans.SaleBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    //atlix.model.beans.***Bean selectedSale = row.getItem();
                    // Aquí abre la subventana
                    //openSaleDetailWindow(selectedSale);
                }
            });
            return row;
        });
    }

    private void openSaleDetailWindow(atlix.model.beans.SaleBean sale) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailRecords.fxml"));
            AnchorPane detailPane = loader.load();

            // Si necesitas pasar el objeto sale al controlador:
            DetailRecordsController controller = loader.getController();
            //controller.setSale(sale);

            Stage stage = new Stage();
            stage.setTitle("Detalle de Venta");
            stage.setScene(new Scene(detailPane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            ShowAlert.INSTANCE.showAlert("ERROR", "Error", "", "No se pudo abrir la ventana de detalle de venta.");
        }
    }
}

