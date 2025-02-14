package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.listeners.DataChangeListener;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Peca;
import com.workshop.model.service.MarcaService;
import com.workshop.model.service.PecaService;
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

public class PecaCadastradasFormController implements Initializable {


    private Peca peca;

    private final PecaService pecaService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public PecaCadastradasFormController() {
        this.pecaService = new PecaService();
    }

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btSave;



    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        try {
            String name = txtName.getText();
            Peca newPeca = new Peca();
            newPeca.setId(Utils.tryParseInt(txtId.getText()));
            newPeca.setNome(name);
            pecaService.insertOrUpdateMarca(newPeca);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (DbException e) {
            Alerts.showAlert("Error Saving Object", null, e.getMessage(), Alert.AlertType.ERROR);
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

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (peca == null)
            throw new IllegalStateException("department is null");
        this.txtId.setText(String.valueOf(peca.getId()));
        this.txtName.setText(peca.getNome());
    }

}
