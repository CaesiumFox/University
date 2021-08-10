package io.github.caesiumfox.lab07.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;

public class NetworkManager {
    private static DatagramChannel datagramChannel;
    private static InetAddress serverAddress;
    private static int serverPort;
    private static SocketAddress socketAddress;
    private static Selector selector;
    public static ByteBuffer byteBuffer;

    public static void init(Scanner input) {
        System.out.println("Enter server address:"
                + (Client.formattedTerminal ? "\u001b[1;32m" : ""));
        while (true) {
            try {
                String addressString = input.nextLine();
                serverAddress = InetAddress.getByName(addressString);
                break;
            } catch (IOException e) {
                if(Client.formattedTerminal) {
                    System.out.print("\u001b[0mThe address is invalid: ");
                    System.out.println("\u001b[1;31m" + e.getMessage() + "\u001b[0m");
                    System.out.println("Enter server address again:\u001b[1;32m");
                } else {
                    System.out.print("The address is invalid: ");
                    System.out.println(e.getMessage());
                    System.out.println("Enter server address again:");
                }
            }
        }
        if(Client.formattedTerminal)
            System.out.print("\u001b[0m");
        System.out.println("Enter server port:");
        if(Client.formattedTerminal)
            System.out.print("\u001b[1;34m");
        while (true) {
            try {
                String addressString = input.nextLine();
                serverPort = Integer.parseInt(addressString);
                break;
            } catch (NumberFormatException e) {
                if(Client.formattedTerminal) {
                    System.out.println("\u001b[0mIt looks like you didn't enter a number.");
                    System.out.println("Enter server port again:\u001b[1;34m");
                } else {
                    System.out.println("It looks like you didn't enter a number.");
                    System.out.println("Enter server port again:");
                }
            }
        }
        if(Client.formattedTerminal)
            System.out.print("\u001b[0m");

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
	public static void send() throws IOException {
		selector.select();
        Iterator iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
            SelectionKey key = (SelectionKey) iter.next();
            iter.remove();
            if(key.isValid()) {
                if(key.isWritable()) {
                    DatagramChannel channel = (DatagramChannel)key.channel();
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
        boolean throwPortUnreachable = false;
        byteBuffer.clear();
        selector.select();
        Iterator iter = selector.selectedKeys().iterator();
        while (iter.hasNext()) {
            SelectionKey key = (SelectionKey) iter.next();
            iter.remove();
            if (key.isValid()) {
                if (key.isReadable()) {
                    DatagramChannel channel = (DatagramChannel) key.channel();
                    try {
                        int channelReturn = channel.read(byteBuffer);
                    } catch(PortUnreachableException e) {
                        throwPortUnreachable = true;
                    }
                    channel.configureBlocking(false);
                    channel.register(key.selector(), SelectionKey.OP_WRITE);
                }
            }
        }
        byteBuffer.flip();
        if(throwPortUnreachable) {
            throw new PortUnreachableException();
        }
    }

    public static String getServerString(boolean colored) {
        StringBuilder result = new StringBuilder();

        if(colored) result.append("\u001b[1;32m");
        String serverString = serverAddress.getHostName();
        if(serverString.contains(":"))
            result.append('[').append(serverString).append(']');
        else
            result.append(serverString);

        if(colored) result.append("\u001b[0m");
        result.append(":");
        if(colored) result.append("\u001b[1;34m");
        result.append(serverPort);
        if(colored) result.append("\u001b[0m");

        return result.toString();
    }
}
