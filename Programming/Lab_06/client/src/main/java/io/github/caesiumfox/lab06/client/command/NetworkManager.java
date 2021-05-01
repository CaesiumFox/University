package io.github.caesiumfox.lab06.client.command;

import io.github.caesiumfox.lab06.client.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Scanner;

public class NetworkManager {
    private static DatagramChannel datagramChannel;
    private static InetAddress serverAddress;
    private static int serverPort;
    private static SocketAddress socketAddress;
    private static Selector selector;
    public static ByteBuffer byteBuffer;

    public static void init(Scanner input) {
        System.out.println("Enter server address:");
        while (true) {
            try {
                String addressString = input.nextLine();
                serverAddress = InetAddress.getByName(addressString);
                break;
            } catch (IOException e) {
                System.out.print("The address is invalid: ");
                System.out.println(e.getMessage());
                System.out.println("Enter server address again:");
            }
        }
        System.out.println("Enter server port:");
        while (true) {
            try {
                String addressString = input.nextLine();
                serverPort = Integer.parseInt(addressString);
                break;
            } catch (NumberFormatException e) {
                System.out.println("It looks like you didn't enter a number.");
                System.out.println("Enter server port again:");
            }
        }
        socketAddress = new InetSocketAddress(serverAddress, serverPort);
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.connect(socketAddress);
            datagramChannel.configureBlocking(false);
            selector = Selector.open();
        } catch(IOException e) {
            System.out.println("Something gone wrong with opening channel.");
            System.out.println("Try to restart the client.");
            System.exit(1);
        }
    }

    /**
     * Отправляет серверу сообщение и принимает ответ.
     * Для отправки и приёма должен быть использован
     * буфер {@link NetworkManager#byteBuffer}.
     * До вызова этого метода необходимо вызвать
     * метод буфера flip(). После буфер
     * уже будет готов к чтению.
     */
    public static void communicate() throws IOException {
        datagramChannel.register(selector, SelectionKey.OP_WRITE);

    }
}
