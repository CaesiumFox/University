package io.github.caesiumfox.lab06.client;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static String dateFormat;
    private static Scanner input;

    static {
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("Starting Client");
        NetworkManager.init(input);
        try {
            NetworkManager.byteBuffer.clear();
            NetworkManager.byteBuffer.putDouble(2.5);
            NetworkManager.byteBuffer.flip();
            NetworkManager.communicate();
            double response = NetworkManager.byteBuffer.getDouble();
            System.out.println(response);
        } catch (IOException e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
        }
    }
}
