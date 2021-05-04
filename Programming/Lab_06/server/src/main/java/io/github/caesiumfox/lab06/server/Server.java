package io.github.caesiumfox.lab06.server;

import io.github.caesiumfox.lab06.common.entry.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Server {
	public static String dateFormat;
	private static Scanner input;

    static {
        dateFormat = "dd.MM.yyyy";
        input = new Scanner(System.in);
        Movie.setDateFormat(dateFormat);
    }

    public static void main(String[] args) {
        System.out.println("Starting Server");
        NetworkManager.init(input);

        try {
            NetworkManager.byteBuffer.clear();
            NetworkManager.receive();
            double num = NetworkManager.byteBuffer.getDouble();

            Movie.RawData response = new Movie.RawData();
            response.id = 5;
            response.name = "Naruto";
            response.coordinates = new Coordinates.RawData();
            response.coordinates.x = 6.6f;
            response.coordinates.y = 7.7f;
            response.creationDate = new java.util.Date();
            response.oscarsCount = 67;
            response.genre = MovieGenre.ACTION;
            response.mpaaRating = MpaaRating.R;
            response.director = new Person.RawData();
            response.director.name = "John Doe";
            response.director.passportID = "1234567890";
            response.director.hairColor = Color.GREEN;


            NetworkManager.byteBuffer.clear();
            response.putInByteBuffer(NetworkManager.byteBuffer);
            NetworkManager.byteBuffer.flip();

            var bb = NetworkManager.byteBuffer.array();
            for(int i = 0; i < NetworkManager.byteBuffer.limit(); i++)
                System.out.print(bb[i] + " ");
            System.out.println();
            System.out.println(NetworkManager.byteBuffer.limit());

            NetworkManager.send();

        } catch (IOException e) {
            System.out.println("Something wrong happened:");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("---=== WOW! ===---");
            System.out.println(e.getMessage());
        }
    }
}
