package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.exceptions.NoRecentClientException;
import io.github.caesiumfox.lab07.common.exceptions.NumberOutOfRangeException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class NetworkManager {
    private static DatagramSocket datagramSocket;
    private static int port;
    private static SocketAddress socketAddress;
    private static SocketAddress recentClient;
    private static byte[] bytes;
    public static ByteBuffer byteBuffer;

    public static void init(Scanner input) {
        Server.logger.info("Started NetworkManager initialization");
        System.out.println("Enter port:");
        while (true) {
            try {
                String portStr = input.nextLine();
                port = Integer.parseInt(portStr);
                if (port < 0 || port > 0xffff)
                    throw new NumberOutOfRangeException(port, 0, 0xffff);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Your input cannot be interpreted as number.");
                System.out.println("Enter port again:");
            } catch (NumberOutOfRangeException e) {
                System.out.println(e.getMessage());
                System.out.println("Enter port again:");
            }
        }
        socketAddress = new InetSocketAddress(port);
        Server.logger.info("Port defined: " + port);
        try {
            datagramSocket = new DatagramSocket(socketAddress);
            Server.logger.info("Initialized datagramSocket");
        } catch (IOException e) {
            System.out.println("Something wrong happened while starting server:");
            System.out.println(e.getMessage());
            Server.logger.severe("Error: " + e.getMessage());
            System.out.println("Try to restart the server.");
            System.exit(1);
        }
        recentClient = null;
        bytes = new byte[8192];
        byteBuffer = ByteBuffer.wrap(bytes);
        Server.logger.info("Finished NetworkManager initialization");
    }

    /**
     * Принимает от клиента сообщение.
     * Для приёма должен быть использован
     * буфер {@link NetworkManager#byteBuffer}.
     * До вызова этого метода необходимо вручную
     * вызвать метод буфера clear().
     */
    public static void receive() throws IOException {
        Server.logger.info("Waiting for a datagram...");
        byteBuffer.clear();
        DatagramPacket inPacket = new DatagramPacket(bytes, byteBuffer.limit());
        datagramSocket.receive(inPacket);
        recentClient = inPacket.getSocketAddress();
        Server.logger.info("Received a datagram from " + recentClient.toString());
    }

    /**
     * Отправляет клиенту ответ.
     * Для приёма должен быть использован
     * буфер {@link NetworkManager#byteBuffer}.
     * До вызова этого метода необходимо вручную
     * вызвать метод буфера flip().
     */
    public static void send() throws IOException {
        Server.logger.info("Sending a datagram to " + recentClient.toString());
        if(recentClient == null)
            throw new NoRecentClientException();
        DatagramPacket outPacket = new DatagramPacket(bytes, byteBuffer.limit(), recentClient);
        datagramSocket.send(outPacket);
        Server.logger.info("Sent the datagram");
    }
}
