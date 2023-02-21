package com.example.login_javafx;

import java.io.*;

import javax.net.ssl.*;

public class Servidor {
    SSLServerSocket serverSocket;
    int puerto = 5556;

    public Servidor(SSLServerSocket servidorSSL) {
        this.serverSocket = servidorSSL;
    }

    public void empezarConexion() {
        try {
            while (!serverSocket.isClosed()) {


                SSLSocket clienteConectado;
                DataOutputStream flujoSalida;
                clienteConectado = (SSLSocket) serverSocket.accept();
                flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());
                // ENVIO UN SALUDO AL CLIENTE
                flujoSalida.writeUTF("IniciarChat");

                ManejoCliente manejoCliente = new ManejoCliente(clienteConectado);
                System.out.println(manejoCliente.nombreCliente + " se ha conectado al servidor!");
                /*Hilo*/
                Thread thread = new Thread(manejoCliente);
                thread.start();
            }
        } catch (IOException e) {

        }
    }

    public void cerrarConexion() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) throws IOException {

        System.setProperty("javax.net.ssl.keyStore", "src/main/java/com/example/login_javafx/AlmacenSrv");
        System.setProperty("javax.net.ssl.keyStorePassword", "1234567");

        //FLUJO DE SALIDA AL CLIENTE
        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact
                .createServerSocket(5556);

        Servidor servidor = new Servidor(servidorSSL);
        System.out.println("Servidor funcionando...");
        servidor.empezarConexion();
    }
}