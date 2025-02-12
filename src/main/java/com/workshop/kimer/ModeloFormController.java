package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Constraints;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Marca;
import com.workshop.model.entities.Modelo;
import com.workshop.model.service.MarcaService;
import com.workshop.model.service.ModeloService;
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

public class ModeloFormController implements Initializable {

    private Modelo modelo;

    private final ModeloService modeloService;
    private final MarcaService marcaService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public ModeloFormController() {
        this.modeloService = new ModeloService();
        this.marcaService = new MarcaService();
    }

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtModelo;

    @FXML
    private ComboBox<Marca> txtMarca;

    @FXML
    private TextField txtCilindrada;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btSave;


    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        try {
            String modelo = txtModelo.getText();
            String cilindrada = txtCilindrada.getText();
            Marca marca = txtMarca.getSelectionModel().getSelectedItem();

            Modelo newModelo = new Modelo();
            newModelo.setId(Utils.tryParseInt(txtId.getText()));
            newModelo.setModelo(modelo);
            newModelo.setCilindrada(cilindrada);
            newModelo.setMarca(marca);

            modeloService.insertOrUpdateMarca(newModelo);

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
        setComboBoxMarca();
    }

    private void setComboBoxMarca() {
        ObservableList<Marca> obsList = FXCollections.observableArrayList(marcaService.findAll());
        txtMarca.setItems(obsList);

        Callback<ListView<Marca>, ListCell<Marca>> factory = lv -> new ListCell<Marca>() {
            @Override
            protected void updateItem(Marca item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNome());
            }
        };

        txtMarca.setCellFactory(factory);
        txtMarca.setButtonCell(factory.call(null));
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtModelo, 30);
        Constraints.setTextFieldMaxLength(txtCilindrada, 30);
        Constraints.setTextFieldMaxLength(txtMarca.getEditor(), 30);
    }

    public void updateFormData() {
        if (modelo == null)
            throw new IllegalStateException("Modelo is null");
        this.txtId.setText(String.valueOf(modelo.getId()));
        this.txtModelo.setText(modelo.getModelo());
        this.txtCilindrada.setText(modelo.getCilindrada());
        this.txtMarca.getSelectionModel().select(modelo.getMarca());

    }
}
