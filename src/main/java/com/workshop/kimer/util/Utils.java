package com.workshop.kimer.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;



public class Utils {
    public static Stage currentStage(ActionEvent event){
         return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }

    public static Integer tryParseInt(String str){
        try {
            return Integer.parseInt(str);
        }catch(NumberFormatException e){
            return null;
        }
    }

    public static String capitalizeWords(String frase) {
        String[] palavras = frase.split(" ");
        StringBuilder novaFrase = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                novaFrase.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return novaFrase.toString().trim();
    }
}
