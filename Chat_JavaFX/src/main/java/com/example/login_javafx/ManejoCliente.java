package com.example.login_javafx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ManejoCliente implements Runnable {

    public static ArrayList<ManejoCliente> clientes = new ArrayList<>();
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String nombreCliente;


    public ManejoCliente(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.nombreCliente = bufferedReader.readLine();
            clientes.add(this);
            enviarMensaje(nombreCliente + " se ha conectado al chat!");
        } catch (IOException e) {
            cerrarTodo(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String mensaje;

        while (socket.isConnected()) {
            try {
                mensaje = bufferedReader.readLine();
                enviarMensaje(mensaje);
            } catch (IOException e) {
                cerrarTodo(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void enviarMensaje(String mensaje) {
        for (ManejoCliente manejoCliente : clientes) {
            try {
                if (!manejoCliente.nombreCliente.equals(nombreCliente)) {
                    manejoCliente.bufferedWriter.write(mensaje);
                    manejoCliente.bufferedWriter.newLine();
                    manejoCliente.bufferedWriter.flush();

                }
            } catch (IOException e) {
                cerrarTodo(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void salidaCliente() {
        clientes.remove(this);
        enviarMensaje(nombreCliente + " ha abandonado el chat");
    }

    public void cerrarTodo(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        salidaCliente();
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


}
//JP

