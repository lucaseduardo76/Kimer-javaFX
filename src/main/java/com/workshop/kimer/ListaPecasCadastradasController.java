package com.workshop.kimer;

import com.workshop.db.DbIntegrityException;
import com.workshop.demo.HelloApplication;
import com.workshop.listeners.DataChangeListener;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Peca;
import com.workshop.model.service.MarcaService;
import com.workshop.model.service.PecaService;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaPecasCadastradasController implements Initializable, DataChangeListener {


    private PecaService pecaService;

    @FXML
    private TableView<Peca> tableViewMarcas;

    @FXML
    private TableColumn<Peca, Integer> tableColumnId;

    @FXML
    private TableColumn<Peca, String> tableColumnName;

    @FXML
    private TableColumn<Peca, Peca> tableColumnEDIT;

    @FXML
    private TableColumn<Peca, Peca> tableColumnDELETE;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Peca obj = new Peca();
        createDialogForm(obj, Utils.currentStage(event));
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
        if(pecaService == null)
            throw new IllegalStateException("Marca service is null");

        List<Peca> list = pecaService.findAll();
        ObservableList<Peca> obsList = FXCollections.observableArrayList(list);
        tableViewMarcas.setItems(obsList);
        initEditButtons();
        deleteButtons();
    }


    private void createDialogForm(Peca obj, Stage parentStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workshop/kimer/PecaForm.fxml"));
            Pane pane = (Pane) loader.load();

            PecaCadastradasFormController controller = loader.getController();
            controller.setPeca(obj);
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
        tableColumnEDIT.setCellFactory(param -> new TableCell<Peca, Peca>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Peca obj, boolean empty) {
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
        tableColumnDELETE.setCellFactory(param -> new TableCell<Peca, Peca>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Peca obj, boolean empty) {
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

    private void deleteAction(Peca obj) {
        try {
            Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
            if(result.isPresent() && result.get() == ButtonType.OK){
                pecaService.delete(obj);
            }
            updateTableView();

        }catch (DbIntegrityException e){
            Alerts.showAlert("DB Exception", "Error deleting department", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void setPecaService(PecaService pecaService) {
        this.pecaService = pecaService;
    }

}
