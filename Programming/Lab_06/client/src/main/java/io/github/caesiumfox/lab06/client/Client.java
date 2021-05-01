package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.client.command.NetworkManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Scanner;

public class Client {
    public static String dateFormat = "dd.MM.yyyy";
    private static Scanner input;

    public static void main(String[] args) {
        System.out.println("Starting Client");
        NetworkManager.init(input);
    }
}
