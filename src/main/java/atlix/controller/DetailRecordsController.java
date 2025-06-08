package atlix.controller;

import atlix.model.beans.SaleBean;
import atlix.model.content.SaleDescription;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class DetailRecordsController {

    @FXML
    private Label lblDetailsId;
    @FXML
    private Label lblDetailsTotal;
    @FXML
    private AnchorPane viewDetails;
    @FXML
    private TableView<SaleDescription> tblDetails;
    @FXML
    private TableColumn<SaleDescription, String> clnDetailsName;
    @FXML
    private TableColumn<SaleDescription, Integer> clnDetailsAmount;
    @FXML
    private TableColumn<SaleDescription, Double> clnDetailsPrice;

    @FXML
    public void initialize() {
        clnDetailsName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        clnDetailsAmount.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getAmount()).asObject());
        clnDetailsPrice.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

    }

    public void setSale(SaleBean bean) {
        lblDetailsId.setText(String.valueOf(bean.getId()));
        lblDetailsTotal.setText(String.format("%.2f", bean.getTotal()));

        List<SaleDescription> descriptions = bean.getDetails();
        tblDetails.setItems(FXCollections.observableArrayList(descriptions));
    }
}