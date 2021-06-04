package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean menuChoice = true;
        while (menuChoice) {
            System.out.println("What do you want to do?");
            System.out.println("1. Show all movies");
            System.out.println("2. Add movie");
            System.out.println("3. Find movie by id");
            System.out.println("4. Find movie by title");
            System.out.println("5. Find movie by length");
            System.out.println("6. Find movie by director");
            System.out.println("7. Find movie by release year");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 0:
                    System.out.println("Thank you, come again.");
                    menuChoice = false;
                    break;
                case 1:
                    talkToServer("GET /getallmovies HTTP/1.1\r\n");
                    break;
                case 2:
                    String movieInformation = addMovie();
                    talkToServer("POST /addmovie?" + movieInformation + " HTTP/1.1\r\n");
                    break;
                case 3:
                    System.out.println("Movie id: ");
                    String movieId = sc.nextLine();
                    talkToServer("GET /findMovieById" + movieId + " HTTP/1.1\r\n");
                    break;
                case 4:
                    System.out.println("Movie title: ");
                    String movieTitle = sc.nextLine();
                    talkToServer("GET /findMovieByTitle" + movieTitle + " HTTP/1.1\r\n");
                    break;
                case 5:
                    System.out.println("Movie length: ");
                    String movieLength = sc.nextLine();
                    talkToServer("GET /findMovieByLength" + movieLength + " HTTP/1.1\r\n");
                    break;
                case 6:
                    System.out.println("Director: ");
                    String director = sc.nextLine();
                    talkToServer("GET /findMovieByDirector" + director + " HTTP/1.1\r\n");
                    break;
                case 7:
                    System.out.println("Movie release year: ");
                    String movieReleaseYear = sc.nextLine();
                    talkToServer("GET /findMovieByReleaseYear" + movieReleaseYear + " HTTP/1.1\r\n");
                    break;
            }
        }

    }

    private static String addMovie() {
        System.out.println("Movie title: ");
        String movieInfo = "movieTitle=" + sc.nextLine();

        System.out.println("Movie length: ");
        movieInfo += "&length=" + sc.nextLine();

        System.out.println("Movie director: ");
        movieInfo += "&director=" + sc.nextLine();

        System.out.println("Release year: ");
        movieInfo += "&releaseYear=" + sc.nextLine();

        movieInfo = movieInfo.replace(" ", "+");

        return movieInfo;
    }


    public static void talkToServer(String header) {
        try {
            Socket socket = new Socket("localhost", 80);

            var output = new PrintWriter(socket.getOutputStream());
            output.print(header);
            output.print("Host: localhost\r\n");
            output.print("\r\n");
            output.flush();

            var inputFromServer = new BufferedReader(new InputStreamReader((socket.getInputStream())));

            boolean heade = true;
            while (true) {
                var line = inputFromServer.readLine();

                if (line == null || line.isEmpty()) {
                    if (heade) {
                        heade = false;
                    } else {
                        break;
                    }
                }

                System.out.println(line);
            }

            inputFromServer.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
