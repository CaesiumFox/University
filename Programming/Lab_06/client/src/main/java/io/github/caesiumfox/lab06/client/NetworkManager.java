package io.github.caesiumfox.lab06.client;

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
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
        } catch(IOException e) {
            System.out.println("Something gone wrong with opening channel.");
            System.out.println("Try to restart the client.");
            System.exit(1);
        }
        byteBuffer = ByteBuffer.allocate(8192);
    }

    /**
     * Отправляет серверу сообщение.
     * Для отправки должен быть использован
     * буфер {@link NetworkManager#byteBuffer}.
     * До вызова этого метода необходимо вызвать
     * метод буфера flip().
     */	
	public static void send() {
		selector.select();
        var iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
            var key = iter.next();
            iter.remove();
            if(key.isValid()) {
                if(key.isWritable()) {
                    var channel = (DatagramChannel)key.channel();
                    int channelReturn = channel.write(byteBuffer);
                    channel.configureBlocking(false);
                    channel.register(key.selector(), SelectionKey.OP_READ);
                }
            }
        }
	}
	
	/**
     * Принимает от сервера ответ.
     * Для приёма должен быть использован
     * буфер {@link NetworkManager#byteBuffer}.
     * После вызова этого метода буфер уже
     * будет готов к чтению.
     */	
    public static void receive() throws IOException {
        byteBuffer.clear();
        selector.select();
        var iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
            var key = iter.next();
            iter.remove();
            if(key.isValid()) {
                if(key.isReadable()) {
                    var channel = (DatagramChannel)key.channel();
                    int channelReturn = channel.read(byteBuffer);
                    channel.configureBlocking(false);
                    channel.register(key.selector(), SelectionKey.OP_WRITE);
                }
            }
        }
        byteBuffer.flip();
    }
}
