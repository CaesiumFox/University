package io.github.caesiumfox.lab06.client;

import java.util.Scanner;

public class Client {
    public static String dateFormat = "dd.MM.yyyy";
    private static Scanner input;

    public static void main(String[] args) {
        System.out.println("Starting Client");
        NetworkManager.init(input);
    }
}
