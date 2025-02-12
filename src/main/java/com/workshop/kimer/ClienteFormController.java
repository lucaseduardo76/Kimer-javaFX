package com.workshop.kimer;

import com.workshop.db.DbException;
import com.workshop.kimer.listeners.DataChangeListener;
import com.workshop.kimer.util.Alerts;
import com.workshop.kimer.util.Constraints;
import com.workshop.kimer.util.Utils;
import com.workshop.model.entities.Cliente;
import com.workshop.model.entities.Marca;
import com.workshop.model.service.ClienteService;
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

public class ClienteFormController implements Initializable {


    private Cliente cliente;

    private final ClienteService clienteService;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public ClienteFormController() {
        this.clienteService = new ClienteService();
    }

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btCancel;

    @FXML
    private Button btSave;



    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void subscribeDataChangeListener(DataChangeListener dataChangeListener) {
        this.dataChangeListeners.add(dataChangeListener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        try {
            String name = txtName.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();

            try {
                Utils.formatarCPF(cpf);
                Utils.formatarTelefone(telefone);
                Utils.validarEmail(email);

                Cliente newCliente = new Cliente();
                newCliente.setId(Utils.tryParseInt(txtId.getText()));
                newCliente.setNome(name);
                newCliente.setCpf(cpf);
                newCliente.setEmail(email);
                newCliente.setTelefone(telefone);

                clienteService.insertOrUpdateMarca(newCliente);

                notifyDataChangeListeners();
                Utils.currentStage(event).close();
            }catch (RuntimeException e){
                Alerts.showAlert("Erro ao formatar texto", "Erro", e.getMessage(), Alert.AlertType.WARNING);
            }
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
        Constraints.setTextFieldMaxLength(txtCpf, 30);
        Constraints.setTextFieldMaxLength(txtEmail, 30);
        Constraints.setTextFieldMaxLength(txtTelefone, 30);
    }

    public void updateFormData() {
        if (cliente == null)
            throw new IllegalStateException("Cliente is null");
        this.txtId.setText(String.valueOf(cliente.getId()));
        this.txtName.setText(cliente.getNome());
        this.txtCpf.setText(cliente.getCpf());
        this.txtEmail.setText(cliente.getEmail());
        this.txtTelefone.setText(cliente.getTelefone());
    }
}
