package com.workshop.kimer;

import com.workshop.demo.HelloApplication;
import com.workshop.listeners.DataChangeListener;
import com.workshop.model.entities.Peca;
import com.workshop.model.entities.PecaNoEstoque;
import com.workshop.model.service.EstoqueService;
import com.workshop.util.Alerts;
import com.workshop.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable, DataChangeListener {


    private EstoqueService estoqueService;

    @FXML
    private TableView<PecaNoEstoque> tableViewMarcas;

    @FXML
    private TableColumn<PecaNoEstoque, Integer> tableColumnId;

    @FXML
    private TableColumn<PecaNoEstoque, Peca> tableColumnPeca;

    @FXML
    private TableColumn<PecaNoEstoque, Double> tableColumnQuantidade;

    @FXML
    private TableColumn<PecaNoEstoque, PecaNoEstoque> tableColumnSAIDA;



    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        PecaNoEstoque obj = new PecaNoEstoque();
        createDialogForm(obj, Utils.currentStage(event));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPeca.setCellValueFactory(new PropertyValueFactory<>("peca"));
        tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        Stage stage = (Stage) HelloApplication.getMainScene().getWindow();
        tableViewMarcas.prefHeightProperty().bind(stage.heightProperty());


    }

    public void updateTableView() {
        if(estoqueService == null)
            throw new IllegalStateException("Marca service is null");

        List<PecaNoEstoque> list = estoqueService.findAll();
        ObservableList<PecaNoEstoque> obsList = FXCollections.observableArrayList(list);
        tableViewMarcas.setItems(obsList);
        initSaidaButtons();
    }


    private void createDialogForm(PecaNoEstoque obj, Stage parentStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workshop/kimer/EstoqueAddForm.fxml"));
            Pane pane = (Pane) loader.load();

            EstoqueAddForm controller = loader.getController();
            controller.subscribeDataChangeListener(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter amout to add");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }catch (IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void createDialogFormDesc(PecaNoEstoque obj, Stage parentStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workshop/kimer/EstoqueDecForm.fxml"));
            Pane pane = (Pane) loader.load();

            EstoqueDecForm controller = loader.getController();
            controller.setPeca(obj);
            controller.updateCombo();
            controller.subscribeDataChangeListener(this);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter amout to remove");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }catch (IOException e){
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @Override
    public void onDataChange() {
        updateTableView();
    }

    private void initSaidaButtons() {
        tableColumnSAIDA.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnSAIDA.setCellFactory(param -> new TableCell<PecaNoEstoque, PecaNoEstoque>() {
            private final Button button = new Button("saida");

            @Override
            protected void updateItem(PecaNoEstoque obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(
                        event -> createDialogFormDesc(
                                obj, Utils.currentStage(event)));
            }
        });
    }




    public void setEstoqueService(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

}
