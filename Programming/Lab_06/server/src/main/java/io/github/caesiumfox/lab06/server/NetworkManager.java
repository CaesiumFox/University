package io.github.caesiumfox.lab06.server;

import io.github.caesiumfox.lab06.common.exceptions.NumberOutOfRangeException;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class NetworkManager {
    private static DatagramSocket datagramSocket;
    private static int port;
    private static SocketAddress socketAddress;

    public static byte[] bytes;
    public static ByteBuffer byteBuffer;

    public static void init(Scanner input) {
        System.out.println("Enter port:");
        while (true) {
            try {
                String portStr = input.nextLine();
                port = Integer.parseInt(portStr);
                if(port < 0 || port > 0xffff)
                    throw new NumberOutOfRangeException(port, 0, 0xffff);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Your input cannot be interpreted as number.");
                System.out.println("Enter port again:");
            } catch (NumberOutOfRangeException e) {
                System.out.println(e.getMessage());
                System.out.println("Enter port again:");
            }
            socketAddress = new InetSocketAddress(port);
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
    }
}
