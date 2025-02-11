package com.workshop.kimer;

import com.workshop.db.DbIntegrityException;
import com.workshop.demo.HelloApplication;
import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Marca;
import com.workshop.model.service.MarcaService;
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

public class ListaMarcasController implements Initializable, DataChangeListener {

    private MarcaService marcaService;

    @FXML
    private TableView<Marca> tableViewMarcas;

    @FXML
    private TableColumn<Marca, Integer> tableColumnId;

    @FXML
    private TableColumn<Marca, String> tableColumnName;

    @FXML
    private TableColumn<Marca, Marca> tableColumnEDIT;

    @FXML
    private TableColumn<Marca, Marca> tableColumnDELETE;

    @FXML
    private Button btNew;

    @FXML
    private ObservableList<Marca> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Marca obj = new Marca();
        createDialogForm(obj, "/com/workshop/kimer/MarcaForm.fxml", Utils.currentStage(event));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));

        Stage stage = (Stage) HelloApplication.getMainScene().getWindow();
        tableViewMarcas.prefHeightProperty().bind(stage.heightProperty());


    }

    public void updateTableView() {
        if(marcaService == null)
            throw new IllegalStateException("Marca service is null");

        List<Marca> list = marcaService.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewMarcas.setItems(obsList);
        initEditButtons();
        deleteButtons();
    }


    private void createDialogForm(Marca obj,String absoluteName, Stage parentStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = (Pane) loader.load();

            MarcaFormController controller = loader.getController();
            controller.setMarca(obj);
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
        tableColumnEDIT.setCellFactory(param -> new TableCell<Marca, Marca>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Marca obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/com/workshop/kimer/MarcaForm.fxml", Utils.currentStage(event)));
            }
        });
    }




    private void deleteButtons() {
        tableColumnDELETE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnDELETE.setCellFactory(param -> new TableCell<Marca, Marca>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Marca obj, boolean empty) {
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

    private void deleteAction(Marca obj) {
        try {
            Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
            if(result.isPresent() && result.get() == ButtonType.OK){
                marcaService.delete(obj);
            }
            updateTableView();

        }catch (DbIntegrityException e){
            Alerts.showAlert("DB Exception", "Error deleting department", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void setMarcaService(MarcaService marcaService) {
        this.marcaService = marcaService;
    }


}
