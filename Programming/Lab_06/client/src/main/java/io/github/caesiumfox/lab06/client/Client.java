package io.github.caesiumfox.lab06.client;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {
    public static String dateFormat;
    public static DatagramChannel datagramChannel;
    public static InetAddress serverAddress;
    public static int serverPort;
    public static Scanner input;

    public static void main(String[] args) {
        System.out.println("Starting Client");
        System.out.println("Enter server address:");
        while (true) {
            try {
                String addressString = input.nextLine();
                serverAddress = InetAddress.getByName(addressString);
            } catch (IOException e) {
				System.out.println(e.getMessage());
            }
        }
    }
}
