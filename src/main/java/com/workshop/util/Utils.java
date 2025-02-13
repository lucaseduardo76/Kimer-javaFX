package com.workshop.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.regex.Pattern;


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

    public static String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new IllegalArgumentException("CPF inválido! Deve conter 11 dígitos numéricos.");
        }
        return cpf.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null || !telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Número de telefone inválido! Deve conter 10 ou 11 dígitos.");
        }

        if (telefone.length() == 11) { // Celular (com 9 dígitos)
            return telefone.replaceFirst("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else { // Telefone fixo (com 8 dígitos)
            return telefone.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
    }

    public static void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("E-mail inválido: campo vazio ou nulo.");
        }

        // Expressão regular para validar e-mail
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(email).matches()) {
            throw new RuntimeException("E-mail inválido: formato incorreto.");
        }
    }

    public static String extractDigits(String input) {
        return input.replaceAll("[^0-9]", "");
    }



}
