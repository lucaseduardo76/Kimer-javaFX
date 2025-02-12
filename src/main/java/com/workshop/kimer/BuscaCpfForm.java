package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Constraints;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Cliente;
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

public class BuscaCpfForm implements Initializable {

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    private final ClienteService clienteService;

    public BuscaCpfForm(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @FXML
    private TextField txtCpf;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btBusca;



    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtBuscaAction(ActionEvent event) {
        try {
            String cpf = txtCpf.getText();

            Cliente cliente = clienteService.findByCpf(cpf);


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
        Constraints.setTextFieldMaxLength(txtCpf, 11);
    }



}
