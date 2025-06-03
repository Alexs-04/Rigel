package atlix.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class DetailRecordsController {

    @FXML
    private Label lblDetailsId;

    @FXML
    private Label lblDetailsTotal;

    @FXML
    private AnchorPane viewDetails;

    @FXML
    private TableView <atlix.model.beans.SaleBean> tblDetails;

    @FXML
    private TableColumn <atlix.model.beans.SaleBean, String> clnDetailsName;
    @FXML
    private TableColumn <atlix.model.beans.SaleBean, String> clnDetailsAmount;
    @FXML
    private TableColumn <atlix.model.beans.SaleBean, String> clnDetailsPrice;


       public void setSale(atlix.model.beans.SaleBean sale) {//SaleBean se va a cambiar por el que corresponda a los registros, que este es solo de ventas
           lblDetailsId.setText(String.valueOf(sale.getId()));
           lblDetailsTotal.setText(String.valueOf(sale.getTotal()));

           //clnDetailsName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductName()));
           //clnDetailsAmount.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
           //clnDetailsPrice.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));

           //tblDetails.setItems(javafx.collections.FXCollections.observableArrayList(sale.getDetailsList()));
       }
   }