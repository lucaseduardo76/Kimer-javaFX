package com.workshop.kimer;

import com.workshop.db.DbException;

import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Constraints;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Marca;
import com.workshop.model.service.MarcaService;
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

public class MarcaFormController implements Initializable {

    private Marca marca;

    private final MarcaService marcaService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public MarcaFormController() {
        this.marcaService = new MarcaService();
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



    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        try {
            String name = txtName.getText();
            Marca newMarca = new Marca();
            newMarca.setId(Utils.tryParseInt(txtId.getText()));
            newMarca.setNome(name);
            marcaService.insertOrUpdateMarca(newMarca);
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
        if (marca == null)
            throw new IllegalStateException("department is null");
        this.txtId.setText(String.valueOf(marca.getId()));
        this.txtName.setText(marca.getNome());
    }
}
