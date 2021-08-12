package io.github.caesiumfox.lab07.server;

import io.github.caesiumfox.lab07.common.exceptions.NumberOutOfRangeException;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkManager extends Thread {
    public static class Packet {
        public SocketAddress client;
        public ByteBuffer buffer;
        public Packet(byte[] bytes, SocketAddress client) {
            this.client = client;
            buffer = ByteBuffer.wrap(bytes);
        }
    }

    public static final int BUFFER_SIZE = 8192; // 2**13
    private DatagramSocket datagramSocket;
    public final Queue<Packet> received;
    public final Queue<Packet> toBeSent;
    private final Object receivingMutex, sendingMutex;

    public NetworkManager(Scanner input) {
        int port;
        SocketAddress socketAddress;
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

        received = new LinkedList<>();
        toBeSent = new LinkedList<>();
        receivingMutex = new Object();
        sendingMutex = new Object();

        Server.logger.info("Finished NetworkManager initialization");
    }

    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            while (Server.running) {
                try {
                    receiveInQueue();
                } catch (IOException e) {
                    Server.logger.info("IO Exception in receive module: " + e.getMessage());
                }
            }
        });
        executor.submit(() -> {
            while (Server.running) {
                try {
                    sendFromQueue();
                } catch (IOException e) {
                    Server.logger.info("IO Exception in send module: " + e.getMessage());
                }
            }
        });
        executor.shutdown();
    }



    /**
     * Принимает от клиента сообщение.
     * До вызова этого метода необходимо вручную
     * вызвать метод буфера clear().
     */
    private void receiveInQueue() throws IOException {
        synchronized (received) {
            byte[] bytes = new byte[NetworkManager.BUFFER_SIZE];
            DatagramPacket inPacket = new DatagramPacket(bytes, NetworkManager.BUFFER_SIZE);
            Server.logger.info("Waiting for a datagram...");
            datagramSocket.receive(inPacket);
            SocketAddress recentClient = inPacket.getSocketAddress();
            received.add(new Packet(bytes, recentClient));
            Server.logger.info("Received a datagram from " + recentClient);
        }
    }

    /**
     * Отправляет клиенту ответ.
     * До вызова этого метода необходимо вручную
     * вызвать метод буфера flip().
     */
    private void sendFromQueue() throws IOException {
        synchronized (toBeSent) {
            while (toBeSent.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
            ByteBuffer buffer = toBeSent.peek().buffer;
            SocketAddress recentClient = toBeSent.poll().client;
            Server.logger.info("Sending a datagram to " + recentClient.toString());
            DatagramPacket outPacket = new DatagramPacket(buffer.array(), buffer.limit(), recentClient);
            datagramSocket.send(outPacket);
            Server.logger.info("Sent the datagram");
        }
    }

    public SocketAddress receive(ByteBuffer buffer) throws InterruptedException {
        synchronized (receivingMutex) {
            while (received.isEmpty()) {
                Thread.sleep(100);
            }
            buffer.clear();
            buffer.put(received.peek().buffer.array());
            buffer.flip();
            assert received.peek() != null;
            return received.poll().client;
        }
    }
    public void send(ByteBuffer buffer, SocketAddress address) {
        synchronized (sendingMutex) {
            toBeSent.add(new Packet(buffer.array(), address));
        }
    }
}
