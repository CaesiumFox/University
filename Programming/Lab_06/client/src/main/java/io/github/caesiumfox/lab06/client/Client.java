package io.github.caesiumfox.lab06.client;

import io.github.caesiumfox.lab06.common.entry.Movie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Client {
    public static String dateFormat;
    private static Scanner input;

    static {
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);
    }

    public static void main(String[] args) {
        System.out.println("Starting Client");
        NetworkManager.init(input);
        try {
            NetworkManager.byteBuffer.clear();
            NetworkManager.byteBuffer.putDouble(2.5);
            NetworkManager.byteBuffer.flip();
            NetworkManager.send();
            NetworkManager.receive();

            var bb = NetworkManager.byteBuffer.array();
            for(var b : bb) {
                System.out.print(b + " ");
            }
            System.out.println();
            System.out.println(bb.length);

            // NetworkManager.byteBuffer.clear();
            Movie.RawData response = new Movie.RawData();
            response.getFromByteBuffer(NetworkManager.byteBuffer);
            Movie m = new Movie(response);
            System.out.println(m.toString());
        } catch (Exception e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
        }
    }
}
