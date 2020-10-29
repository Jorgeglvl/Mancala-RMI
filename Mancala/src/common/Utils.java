package common;

import java.io.*;
import java.net.Socket;

public class Utils {
    public static boolean sendMessage(Socket connection, String message) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            output.writeObject(message);
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR:sendMessage] -> " + e.getMessage());
        }
        return false;
    }

    public static String receiveMessage(Socket connection) {
        String response = null;
        try {
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            response = (String) input.readObject();
        } catch (IOException e) {
            System.err.println("[ERROR:receivedMessage] -> " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR:receivedMessage] -> " + e.getMessage());
        }

        return response;
    }

    // Converte um array de int (board) para String, separando por "-"
    public static String boardToString(int[] board) {

        String string = "";

        for (int i = 0; i < board.length; i++) {
            string += board[i] + "-";
        }

        return string;
    }

    // Converte uma String de inteiros separados por "-" para uma matriz de int
    public static int[] stringToBoard(String string) {

        int[] board = new int[7];
        int n = 0;

        for (int i = 0; i < board.length; i++) {
            if (string.charAt(n + 1) == '-') {
                board[i] = Integer.parseInt("" + string.charAt(n));
                n += 2;
            } else if (string.charAt(n + 1) != '-') {
                board[i] = Integer.parseInt("" + string.charAt(n) + string.charAt(n + 1));
                n += 3;
            }
        }

        return board;
    }

}
