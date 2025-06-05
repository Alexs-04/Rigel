package atlix.controller;

import atlix.logic.services.LoadViewService;
import atlix.logic.services.SaleService;
import atlix.model.beans.ProductBean;
import atlix.model.beans.SaleBean;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    private TableColumn<SaleBean, String> clnNameEarning;
    @FXML
    private TableColumn<SaleBean, String> clnPriceEarning;
    @FXML
    private TableColumn<SaleBean, String> clnAmountEarning;
    @FXML
    private TableColumn<SaleBean, String> clnIdRecord;
    @FXML
    private TableColumn<SaleBean, String> clnDateRecord;
    @FXML
    private TableColumn<SaleBean, String> clnTotalRecord;
    @FXML
    private TableColumn<SaleBean, String> clnAmountRecord;
    @FXML
    private TableView<SaleBean> tblSaleEarning;
    @FXML
    private TableView<SaleBean> tblSaleRecord;
    @FXML
    private ComboBox<String> cbxEarning;

    @FXML
    private AnchorPane viewEarnings;

    @FXML
    private AnchorPane viewRecords;

    private final SaleService saleService = new SaleService();
    private final LoadViewService loadViewService = new LoadViewService();

    @FXML
    public void initialize() {
        configureEvents();
        recordTable();
        setDataToRecordTable();
    }

    public void configureEvents() {
        btnMenuHome.setOnAction(event -> goToHome());
        btnMenuSale.setOnAction(event -> goToSale());
        btnMenuRecord.setOnAction(event -> goToRecord());
        btnMenuEarnings.setOnAction(event -> goToEarnings());
    }

    public void goToSale() {
        loadViewService.loadSalesView();
        var stage = (Stage) btnMenuSale.getScene().getWindow();
        loadViewService.closeWindow(stage);
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
        loadViewService.loadMainView();
        var stage = (Stage) btnMenuHome.getScene().getWindow();
        loadViewService.closeWindow(stage);
    }

    public void earningTable() {
    }

    public void recordTable() {
        tblSaleRecord.setRowFactory(tv -> {
            TableRow<SaleBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    SaleBean sale = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailRecords.fxml"));
                        Parent root = loader.load();
                        DetailRecordsController controller = loader.getController();
                        controller.setSale(sale); // Método que debes crear en el controlador

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Detalle de Venta");
                        stage.show();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            return row;
        });
    }

    public void setDataToRecordTable() {
        clnIdRecord.setCellValueFactory(new PropertyValueFactory<>("id"));
        clnDateRecord.setCellValueFactory(new PropertyValueFactory<>("date"));
        clnTotalRecord.setCellValueFactory(new PropertyValueFactory<>("total"));
        clnAmountRecord.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(cellData.getValue().getDetails().size())
        ));

        tblSaleRecord.setItems(FXCollections.observableArrayList(saleService.getAllSales()));
    }
}

