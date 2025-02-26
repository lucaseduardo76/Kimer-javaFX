package com.workshop.kimer;

import com.workshop.db.DbIntegrityException;
import com.workshop.demo.HelloApplication;
import com.workshop.listeners.DataChangeListener;
import com.workshop.util.Alerts;
import com.workshop.util.Utils;
import com.workshop.model.entities.Cliente;
import com.workshop.model.service.ClienteService;
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
import java.util.function.Consumer;

public class ListaClientesController implements Initializable, DataChangeListener {

    private Boolean busca;

    private List<Cliente> listaBusca;

    private ClienteService clienteService;

    public ListaClientesController() {
        this.busca = false;
    }

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
    private Button btBuscaCpf;

    @FXML
    private Button btBuscaNome;

    @FXML
    private Button btLimpaBusca;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Cliente obj = new Cliente();
        createDialogForm(obj, Utils.currentStage(event), "/com/workshop/kimer/ClienteForm.fxml");
    }
    @FXML
    public void onBtBuscaCpf(ActionEvent event) {
        Cliente obj = new Cliente();
        createDialogFormBusca( Utils.currentStage(event), "/com/workshop/kimer/BuscaCPFForm.fxml", (BuscaCpfForm controller) -> {
            controller.subscribeController(this);
            controller.setClienteService(clienteService);
            controller.subscribeDataChangeListener(this);
        });
    }

    @FXML
    public void onBtBuscaNome(ActionEvent event) {
        Cliente obj = new Cliente();
        createDialogFormBusca( Utils.currentStage(event), "/com/workshop/kimer/BuscaNomeForm.fxml", (BuscaNomeForm controller) -> {
            controller.subscribeController(this);
            controller.setClienteService(clienteService);
            controller.subscribeDataChangeListener(this);
        });
    }

    @FXML
    public void onBtLimpaBusca(ActionEvent event) {
        this.busca = false;
        this.listaBusca = null;
        updateTableView();
    }

    public void setListaBusca(List<Cliente> listaBusca) {
        this.listaBusca = listaBusca;
    }

    public void setBusca(Boolean busca) {
        this.busca = busca;
    }

    private synchronized <T> void createDialogFormBusca(Stage parentStage, String path, Consumer<T> initializationAction) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = (Pane) loader.load();

            initializationAction.accept(loader.getController());


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }catch (IOException e){
            System.out.println(e.getMessage());
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
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

        if(busca && listaBusca != null){
            list = listaBusca;
        }else{
            list = clienteService.findAll();
        }

        ObservableList<Cliente> obsList = FXCollections.observableArrayList(list);
        tableViewClientes.setItems(obsList);
        initEditButtons();
        deleteButtons();
    }


    private void createDialogForm(Cliente obj, Stage parentStage, String path) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
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
                                obj, Utils.currentStage(event), "/com/workshop/kimer/ClienteForm.fxml"));
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
