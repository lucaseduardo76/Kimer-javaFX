package com.workshop.kimer;

import com.workshop.db.DbIntegrityException;
import com.workshop.demo.HelloApplication;
import com.workshop.listeners.DataChangeListener;
import com.workshop.model.Dto.MotoClienteDTO;
import com.workshop.model.entities.Cliente;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Modelo;
import com.workshop.model.entities.MotoCliente;
import com.workshop.model.service.ClienteService;
import com.workshop.model.service.MotoClienteService;
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
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

@Setter
public class ListaMotoClienteController implements Initializable, DataChangeListener {

    private Boolean busca;

    private List<MotoCliente> listaBusca;

    @Setter
    private MotoClienteService motoClienteService;

    public ListaMotoClienteController() {
        this.busca = false;
    }

    @FXML
    private TableView<MotoClienteDTO> tableViewClientes;

    @FXML
    private TableColumn<MotoClienteDTO, Integer> tableColumnId;

    @FXML
    private TableColumn<MotoClienteDTO, String> tableColumnPlaca;

    @FXML
    private TableColumn<MotoClienteDTO, String> tableColumnAno;

    @FXML
    private TableColumn<MotoClienteDTO, Modelo> tableColumnModelo;

    @FXML
    private TableColumn<MotoClienteDTO, Marca> tableColumnMarca;

    @FXML
    private TableColumn<MotoClienteDTO, Cliente> tableColumnDono;

    @FXML
    private TableColumn<MotoClienteDTO, MotoClienteDTO> tableColumnEDIT;

    @FXML
    private TableColumn<MotoClienteDTO, MotoClienteDTO> tableColumnDELETE;

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
        MotoClienteDTO obj = MotoClienteDTO.builder().build();
        createDialogForm(obj, Utils.currentStage(event), "/com/workshop/kimer/ClienteForm.fxml");
    }
//    @FXML
//    public void onBtBuscaCpf(ActionEvent event) {
//        Cliente obj = new Cliente();
//        createDialogFormBusca( Utils.currentStage(event), "/com/workshop/kimer/BuscaCPFForm.fxml", (BuscaCpfForm controller) -> {
//            controller.subscribeController(this);
//            controller.setClienteService(motoClienteService);
//            controller.subscribeDataChangeListener(this);
//        });
//    }
//
//    @FXML
//    public void onBtBuscaNome(ActionEvent event) {
//        Cliente obj = new Cliente();
//        createDialogFormBusca( Utils.currentStage(event), "/com/workshop/kimer/BuscaNomeForm.fxml", (BuscaNomeForm controller) -> {
//            controller.subscribeController(this);
//            controller.setClienteService(motoClienteService);
//            controller.subscribeDataChangeListener(this);
//        });
//    }

    @FXML
    public void onBtLimpaBusca(ActionEvent event) {
        this.busca = false;
        this.listaBusca = null;
        updateTableView();
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
        tableColumnPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        tableColumnAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tableColumnModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnDono.setCellValueFactory(new PropertyValueFactory<>("dono"));

        Stage stage = (Stage) HelloApplication.getMainScene().getWindow();
        tableViewClientes.prefHeightProperty().bind(stage.heightProperty());


    }

    public void updateTableView() {
        if(motoClienteService == null)
            throw new IllegalStateException("Marca service is null");
        List<MotoCliente> list;

        if(busca && listaBusca != null){
            list = listaBusca;
        }else{
            list = motoClienteService.findAll();
        }

        List<MotoClienteDTO> listDTO = list.stream().map(moto ->
                MotoClienteDTO.builder()
                        .id(moto.getId())
                        .placa(moto.getPlaca())
                        .ano(moto.getAno())
                        .modelo(moto.getModelo())
                        .marca(moto.getModelo().getMarca())
                        .dono(moto.getCliente())
                        .build()
        ).toList();

        ObservableList<MotoClienteDTO> obsList = FXCollections.observableArrayList(listDTO);
        tableViewClientes.setItems(obsList);
        initEditButtons();
        deleteButtons();
    }


    private void createDialogForm(MotoClienteDTO obj, Stage parentStage, String path) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Pane pane = (Pane) loader.load();

//            MotoClienteFormController controller = loader.getController();
//            controller.setCliente(obj);
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();


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
        tableColumnEDIT.setCellFactory(param -> new TableCell<MotoClienteDTO, MotoClienteDTO>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(MotoClienteDTO obj, boolean empty) {
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
        tableColumnDELETE.setCellFactory(param -> new TableCell<MotoClienteDTO, MotoClienteDTO>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(MotoClienteDTO obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                MotoCliente moto = motoClienteService.findById(obj.getId());

                setGraphic(button);
                button.setOnAction(event -> deleteAction(moto));
            }

        });
    }

    private void deleteAction(MotoCliente obj) {
        try {
            Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
            if(result.isPresent() && result.get() == ButtonType.OK){
                motoClienteService.delete(obj);
            }
            updateTableView();

        }catch (DbIntegrityException e){
            Alerts.showAlert("DB Exception", "Error deleting Cliente", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

}
