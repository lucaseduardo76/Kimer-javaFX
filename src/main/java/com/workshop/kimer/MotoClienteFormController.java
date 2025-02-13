package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.listeners.DataChangeListener;
import com.workshop.model.entities.Cliente;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Modelo;
import com.workshop.model.entities.MotoCliente;
import com.workshop.model.service.ClienteService;
import com.workshop.model.service.ModeloService;
import com.workshop.model.service.MotoClienteService;
import com.workshop.util.Alerts;
import com.workshop.util.Constraints;
import com.workshop.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MotoClienteFormController implements Initializable {

    private MotoCliente motoCliente;

    private final MotoClienteService motorClienteService;

    private final ModeloService modeloService;

    private final ClienteService clienteService;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public MotoClienteFormController() {
        this.motorClienteService = new MotoClienteService();
        this.modeloService = new ModeloService();
        this.clienteService = new ClienteService();
    }

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtAno;

    @FXML
    private ComboBox<Modelo> comboBoxModelo;

    @FXML
    private ComboBox<Cliente> comboBoxCliente;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btSave;



    public void setMotoCliente(MotoCliente motoCliente) {
        this.motoCliente = motoCliente;
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        try {
            String placa = Utils.removerCaracteresEspeciais(txtPlaca.getText());
            Integer ano = Integer.parseInt(txtAno.getText());
            Modelo modelo = comboBoxModelo.getSelectionModel().getSelectedItem();
            Cliente cliente = comboBoxCliente.getSelectionModel().getSelectedItem();


            MotoCliente motoClienteToSend = new MotoCliente();
            motoClienteToSend.setPlaca(placa);
            motoClienteToSend.setAno(ano);
            motoClienteToSend.setModelo(modelo);
            motoClienteToSend.setCliente(cliente);
            motoClienteToSend.setId(motoCliente.getId());

//            MotoCliente motocliente = MotoCliente.builder()
//                    .id(motoCliente.getId())
//                    .placa(placa)
//                    .cliente(cliente)
//                    .ano(ano)
//                    .modelo(modelo)
//                    .build();

            motorClienteService.insertOrUpdateMarca(motoClienteToSend);

            notifyDataChangeListeners();
            Utils.currentStage(event).close();

        }catch (DbException e) {
            Alerts.showAlert("Error Saving Object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (RuntimeException e){
            Alerts.showAlert("Erro ao formatar texto", "Erro", e.getMessage(), Alert.AlertType.WARNING);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener dataChangeListener : dataChangeListeners) {
            dataChangeListener.onDataChange();
        }
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {

        Utils.currentStage(event).close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
        setComboBoxModelo();
        setComboBoxCliente();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtAno, 30);
        Constraints.setTextFieldMaxLength(txtPlaca, 30);;
        Constraints.setTextFieldMaxLength(comboBoxCliente.getEditor(), 30);
        Constraints.setTextFieldMaxLength(comboBoxModelo.getEditor(), 30);
    }

    private void setComboBoxModelo() {
        ObservableList<Modelo> obsList = FXCollections.observableArrayList(modeloService.findAll());
        comboBoxModelo.setItems(obsList);

        Callback<ListView<Modelo>, ListCell<Modelo>> factory = lv -> new ListCell<Modelo>() {
            @Override
            protected void updateItem(Modelo item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        };

        comboBoxModelo.setCellFactory(factory);
        comboBoxModelo.setButtonCell(factory.call(null));
    }

    private void setComboBoxCliente() {
        ObservableList<Cliente> obsList = FXCollections.observableArrayList(clienteService.findAll());
        comboBoxCliente.setItems(obsList);

        Callback<ListView<Cliente>, ListCell<Cliente>> factory = lv -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        };

        comboBoxCliente.setCellFactory(factory);
        comboBoxCliente.setButtonCell(factory.call(null));
    }

    public void updateFormData() {
        if (motoCliente == null)
            throw new IllegalStateException("Cliente is null");
        this.txtId.setText(String.valueOf(motoCliente.getId()));
        if(motoCliente.getAno() != null)
            this.txtAno.setText(motoCliente.getAno().toString());
        this.txtPlaca.setText(motoCliente.getPlaca());
        this.comboBoxCliente.getSelectionModel().select(motoCliente.getCliente());
        this.comboBoxModelo.getSelectionModel().select(motoCliente.getModelo());
    }

}
