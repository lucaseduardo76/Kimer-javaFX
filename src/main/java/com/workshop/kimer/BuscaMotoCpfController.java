package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.listeners.DataChangeListener;
import com.workshop.model.service.ClienteService;
import com.workshop.model.service.MotoClienteService;
import com.workshop.util.Alerts;
import com.workshop.util.Constraints;
import com.workshop.util.Utils;
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

public class BuscaMotoCpfController implements Initializable {


    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    private ListaMotoClienteController listaMotoClienteController;

    private MotoClienteService motoClienteService;



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

    public void subscribeController(ListaMotoClienteController listaMotoClienteController) {
        this.listaMotoClienteController = listaMotoClienteController;
    }

    @FXML
    public void onBtBuscaAction(ActionEvent event) {
        try {
            String cpf = Utils.extractDigits(txtCpf.getText());


            listaMotoClienteController.setListaBusca(motoClienteService.findByClienteCpf(cpf));
            listaMotoClienteController.setBusca(true);

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

    public void setMotoClienteService(MotoClienteService motoClienteService) {
        this.motoClienteService = motoClienteService;
    }
}
