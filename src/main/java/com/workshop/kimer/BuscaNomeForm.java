package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.listeners.DataChangeListener;
import com.workshop.util.Alerts;
import com.workshop.util.Constraints;
import com.workshop.util.Utils;
import com.workshop.model.service.ClienteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuscaNomeForm implements Initializable {
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    private ListaClientesController listaClientesController;

    private ClienteService clienteService;



    @FXML
    private TextField txtNome;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btBusca;



    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    public void subscribeController(ListaClientesController listaClientesController) {
        this.listaClientesController = listaClientesController;
    }

    @FXML
    public void onBtBuscaAction(ActionEvent event) {
        try {

            String nome = txtNome.getText();
            listaClientesController.setListaBusca(clienteService.findByName(nome));
            listaClientesController.setBusca(true);

            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error Searching Object", null, e.getMessage(), Alert.AlertType.ERROR);
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
    }

    private void initializeNodes() {
        Constraints.setTextFieldMaxLength(txtNome, 50);
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
}
