package com.workshop.kimer;

import com.workshop.listeners.DataChangeListener;
import com.workshop.model.entities.Modelo;
import com.workshop.model.entities.Peca;
import com.workshop.model.entities.PecaNoEstoque;
import com.workshop.model.service.EstoqueService;
import com.workshop.model.service.PecaService;
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

public class EstoqueDecForm implements Initializable {

    private PecaNoEstoque peca;

    private final EstoqueService estoqueService;
    private final PecaService pecaService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public EstoqueDecForm() {
        this.estoqueService = new EstoqueService();
        this.pecaService = new PecaService();
    }

    @FXML
    private TextField txtQuantidade;

    @FXML
    private ComboBox<Peca> comboBoxPeca;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btSave;


    public void setPeca(PecaNoEstoque peca) {
        this.peca = peca;
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        try {

            Double quantidade = Utils.parseDouble(txtQuantidade.getText());
            Peca peca = comboBoxPeca.getSelectionModel().getSelectedItem();

            PecaNoEstoque newPecaNoEstoque = new PecaNoEstoque();
            newPecaNoEstoque.setPeca(peca);
            newPecaNoEstoque.addQuantidade(quantidade);

            estoqueService.decreaseByIdPeca(newPecaNoEstoque);

            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        }
        catch (RuntimeException e) {
            Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
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
        ObservableList<Peca> obsList = FXCollections.observableArrayList(pecaService.findAll());
        comboBoxPeca.setItems(obsList);

        Callback<ListView<Peca>, ListCell<Peca>> factory = lv -> new ListCell<Peca>() {
            @Override
            protected void updateItem(Peca item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNome());
            }
        };

        comboBoxPeca.setCellFactory(factory);
        comboBoxPeca.setButtonCell(factory.call(null));
    }

    private void initializeNodes(){
        Constraints.setTextFieldDouble(txtQuantidade);
        Constraints.setTextFieldMaxLength(comboBoxPeca.getEditor(), 30);
    }

    public void updateCombo(){
        if(peca != null){
            comboBoxPeca.getSelectionModel().select(peca.getPeca());
            comboBoxPeca.setDisable(true);
        }
    }


}
