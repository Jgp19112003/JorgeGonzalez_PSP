package com.example.login_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import javax.net.ssl.*;

public class Cliente {

    SSLSocket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String nombre;

    /*Constructor CLIENTE*/
    public Cliente(SSLSocket socket, String nombre) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombre = nombre;
        } catch (Exception e) {
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }

    /*Metodo que permite a los clientes enviar mensajes*/
    public void enviarMensaje() {
        try {
            bufferedWriter.write(nombre);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String mensaje = scanner.nextLine();
                System.out.printf("Tu: " + mensaje);
                System.out.println();
                bufferedWriter.write(nombre + ": " + mensaje);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }

    /*Metodo que permite a los clientes recibir mensajes*/
    public void recibirMensaje() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String mensaje;
                while (socket.isConnected()) {
                    try {
                        mensaje = bufferedReader.readLine();
                        System.out.println(mensaje);
                    } catch (Exception e) {
                        cerrarTodo(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    /*Metodo para cerrar conexiones*/
    public void cerrarTodo(SSLSocket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {

    }

}