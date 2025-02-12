package com.workshop.kimer;

import com.workshop.db.DbIntegrityException;
import com.workshop.demo.HelloApplication;
import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Modelo;
import com.workshop.model.service.ModeloService;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaModelosController implements Initializable, DataChangeListener {

    private ModeloService modeloService;

    @FXML
    private TableView<Modelo> tableViewModelos;

    @FXML
    private TableColumn<Modelo, Integer> tableColumnId;

    @FXML
    private TableColumn<Modelo, String> tableColumnModelo;

    @FXML
    private TableColumn<Modelo, String> tableColumnCilindrada;

    @FXML
    private TableColumn<Modelo, String> tableColumnMarca;

    @FXML
    private TableColumn<Modelo, Modelo> tableColumnEDIT;

    @FXML
    private TableColumn<Modelo, Modelo> tableColumnDELETE;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Modelo obj = new Modelo();
        createDialogForm(obj, Utils.currentStage(event));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableColumnCilindrada.setCellValueFactory(new PropertyValueFactory<>("cilindrada"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        Stage stage = (Stage) HelloApplication.getMainScene().getWindow();
        tableViewModelos.prefHeightProperty().bind(stage.heightProperty());


    }

    public void updateTableView() {
        if(modeloService == null)
            throw new IllegalStateException("Modelo service is null");

        List<Modelo> list = modeloService.findAll();
        ObservableList<Modelo> obsList = FXCollections.observableArrayList(list);
        tableViewModelos.setItems(obsList);
        initEditButtons();
        deleteButtons();
    }


    private void createDialogForm(Modelo obj, Stage parentStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workshop/kimer/ModeloForm.fxml"));
            Pane pane = (Pane) loader.load();

            ModeloFormController controller = loader.getController();
            controller.setModelo(obj);
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
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

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Modelo, Modelo>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Modelo obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, Utils.currentStage(event)));
            }
        });
    }




    private void deleteButtons() {
        tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnDELETE.setCellFactory(param -> new TableCell<Modelo, Modelo>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Modelo obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> deleteAction(obj));
            }

        });
    }

    private void deleteAction(Modelo obj) {
        try {
            Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
            if(result.isPresent() && result.get() == ButtonType.OK){
                modeloService.delete(obj);
            }
            updateTableView();

        }catch (DbIntegrityException e){
            Alerts.showAlert("DB Exception", "Error deleting department", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void setModeloService(ModeloService modeloService) {
        this.modeloService = modeloService;
    }


}
