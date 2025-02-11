package com.workshop.kimer;

import com.workshop.demo.HelloApplication;
import com.workshop.exception.UsuarioNull;
import com.workshop.kimer.util.Alerts;
import com.workshop.model.entities.Usuario;
import com.workshop.model.service.UsuarioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class LoginController implements Initializable {

    private UsuarioService usuarioService;

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void onLoginButtonAction(ActionEvent event) {

        try{
            Usuario usuario = new Usuario();
            usuario.setUsername(usernameField.getText());
            usuario.setSenha(passwordField.getText());
            usuarioService.login(usuario);

            loadView("/com/workshop/kimer/MainView.fxml", x -> {
            });
        }catch (UsuarioNull e){
            Alerts.showAlert("Login Inválido", e.getMessage(), null, Alert.AlertType.ERROR);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
        try {
            VBox newVbox = loader.load();
            Scene mainScene = HelloApplication.getMainScene();

            // Ajustar tamanho da janela
            mainScene.getWindow().setWidth(1150);
            mainScene.getWindow().setHeight(700);

            // Pegar o ScrollPane principal
            ScrollPane scrollPane = (ScrollPane) mainScene.getRoot();
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setPadding(Insets.EMPTY); // Remover qualquer padding extra

            // Pegar o VBox principal dentro do ScrollPane
            VBox mainVbox = (VBox) scrollPane.getContent();
            mainVbox.setPadding(Insets.EMPTY); // Remover padding
            mainVbox.setSpacing(0); // Remover espaçamento extra

            // Garantir que a nova tela ocupa todo o espaço disponível
            newVbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            VBox.setVgrow(newVbox, Priority.ALWAYS);

            // Substituir conteúdo
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(newVbox); // Adiciona a nova tela

            initializingAction.accept(loader.getController());
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


}
