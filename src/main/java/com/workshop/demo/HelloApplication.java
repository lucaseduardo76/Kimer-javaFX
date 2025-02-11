package com.workshop.demo;

import com.workshop.kimer.LoginController;
import com.workshop.model.service.UsuarioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/workshop/kimer/login.fxml"));
            ScrollPane scrollPane = loader.load();
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            LoginController controller = loader.getController();
            controller.setUsuarioService(new UsuarioService());

            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Kimer System");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}