package com.workshop.kimer;

import com.workshop.demo.HelloApplication;
import com.workshop.model.service.MotoClienteService;
import com.workshop.util.Alerts;
import com.workshop.model.entities.MotoCliente;
import com.workshop.model.service.ClienteService;
import com.workshop.model.service.MarcaService;
import com.workshop.model.service.ModeloService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem btListaClientes;

    @FXML
    private MenuItem btListaMarca;

    @FXML
    private MenuItem btListaModelo;

    @FXML
    private MenuItem btListaMotoCliente;

    @FXML
    private MenuItem btListaPecasCadastrada;

    @FXML
    private MenuItem btEstoque;

    @FXML
    private MenuItem btOrcamento;

    @FXML
    private MenuItem btSair;

    @FXML
    private MenuItem btHome;

    @FXML
    private Pane contentArea;


    @FXML
    private void onBtListaMarca() {
        loadView("/com/workshop/kimer/listaMarcas.fxml", (ListaMarcasController listaController) -> {
            listaController.setListaMarca(new MarcaService());
            listaController.updateTableView();
        });
    }

    @FXML
    private void onBtListaClientes() {
        loadView("/com/workshop/kimer/listaClientes.fxml", (ListaClientesController listaController) -> {
            listaController.setClienteService(new ClienteService());
            listaController.updateTableView();
        });
    }

    @FXML
    private void onBtHome() {
        loadFullView("/com/workshop/kimer/MainView.fxml");
    }

    @FXML
    private void onBtListaModelo() {
        loadView("/com/workshop/kimer/listaModelos.fxml", (ListaModelosController listaController) -> {
            listaController.setModeloService(new ModeloService());
            listaController.updateTableView();
        });
    }

    @FXML
    private void onBtListaMotoCliente() {
        loadView("/com/workshop/kimer/listaMotoCliente.fxml", (ListaMotoClienteController listaController) -> {
        listaController.setMotoClienteService(new MotoClienteService());
        listaController.updateTableView();
    });
    }

    @FXML
    private void onBtListaPecasCadastrada() {
        // Implementação do método
    }

    @FXML
    private void onBtEstoque() {
        // Implementação do método
    }

    @FXML
    private void onBtOrcamento() {
        // Implementação do método
    }

    @FXML
    private void onBtSair() {
        // Implementação do método
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
        try {
            Node newView = loader.load();


            contentArea.getChildren().clear();
            contentArea.getChildren().add(newView);

            initializingAction.accept(loader.getController());
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private synchronized void loadFullView(String absoluteName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
        try {
            VBox newRoot = loader.load(); // Carrega a nova view completa

            // Obtém a cena principal e redefine o root (remove tudo)
            Scene mainScene = HelloApplication.getMainScene();
            mainScene.setRoot(newRoot); // Remove tudo e insere a nova view

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading full view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
