package com.workshop.kimer;

import com.workshop.db.DbIntegrityException;
import com.workshop.demo.HelloApplication;
import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Cliente;
import com.workshop.model.entities.Marca;
import com.workshop.model.service.ClienteService;
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

public class ListaClientesController implements Initializable, DataChangeListener {

    private Boolean busca;

    private List<Cliente> listaBusca;

    private ClienteService clienteService;

    @FXML
    private TableView<Cliente> tableViewClientes;

    @FXML
    private TableColumn<Cliente, Integer> tableColumnId;

    @FXML
    private TableColumn<Cliente, String> tableColumnName;

    @FXML
    private TableColumn<Cliente, String> tableColumnCpf;

    @FXML
    private TableColumn<Cliente, String> tableColumnEmail;

    @FXML
    private TableColumn<Cliente, String> tableColumnTelefone;

    @FXML
    private TableColumn<Cliente, Cliente> tableColumnEDIT;

    @FXML
    private TableColumn<Cliente, Cliente> tableColumnDELETE;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Cliente obj = new Cliente();
        createDialogForm(obj, Utils.currentStage(event));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        Stage stage = (Stage) HelloApplication.getMainScene().getWindow();
        tableViewClientes.prefHeightProperty().bind(stage.heightProperty());


    }

    public void updateTableView() {
        if(clienteService == null)
            throw new IllegalStateException("Marca service is null");
        List<Cliente> list;

        if(!busca) {
            list = clienteService.findAll();
        }else{
            list = listaBusca;
        }
        ObservableList<Cliente> obsList = FXCollections.observableArrayList(list);
        tableViewClientes.setItems(obsList);
        initEditButtons();
        deleteButtons();
    }


    private void createDialogForm(Cliente obj, Stage parentStage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workshop/kimer/ClienteForm.fxml"));
            Pane pane = (Pane) loader.load();

            ClienteFormController controller = loader.getController();
            controller.setCliente(obj);
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
        tableColumnEDIT.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Cliente obj, boolean empty) {
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
        tableColumnDELETE.setCellFactory(param -> new TableCell<Cliente, Cliente>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Cliente obj, boolean empty) {
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

    private void deleteAction(Cliente obj) {
        try {
            Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
            if(result.isPresent() && result.get() == ButtonType.OK){
                clienteService.delete(obj);
            }
            updateTableView();

        }catch (DbIntegrityException e){
            Alerts.showAlert("DB Exception", "Error deleting Cliente", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }



}
