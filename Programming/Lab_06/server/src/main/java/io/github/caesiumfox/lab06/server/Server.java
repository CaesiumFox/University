package io.github.caesiumfox.lab06.server;

import java.io.IOException;
import java.util.Scanner;

public class Server {
	public static String dateFormat;
	private static Scanner input;

    static {
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("Starting Server");
        NetworkManager.init(input);

        try {
            NetworkManager.byteBuffer.clear();
            NetworkManager.receive();
            double num = NetworkManager.byteBuffer.getDouble();
            num *= num;
            NetworkManager.byteBuffer.clear();
            NetworkManager.byteBuffer.putDouble(num);
            NetworkManager.byteBuffer.flip();
            NetworkManager.send();

        } catch (IOException e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
        }
    }
}
