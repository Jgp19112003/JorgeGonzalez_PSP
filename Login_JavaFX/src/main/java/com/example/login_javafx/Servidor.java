package com.example.login_javafx;

import java.io.*;
import javax.net.ssl.*;

public class Servidor {

    public static void main(String[] arg) throws IOException {
        int puerto = 5556;

        System.setProperty("javax.net.ssl.keyStore","C:\\Users\\jgp19\\Desktop\\GitHub\\JorgeGonzalez_PSP\\Login_JavaFX\\src\\main\\java\\com\\example\\login_javafx\\AlmacenSrv");
        System.setProperty("javax.net.ssl.keyStorePassword","1234567");

        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact
                .createServerSocket(puerto);
        SSLSocket clienteConectado = null;
        DataOutputStream flujoSalida = null;//FLUJO DE SALIDA AL CLIENTE

        for (int i = 1; i < 3; i++) {
            System.out.println("Esperando al cliente " + i);
            clienteConectado = (SSLSocket) servidorSSL.accept();

            flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());

            // ENVIO UN SALUDO AL CLIENTE
            flujoSalida.writeUTF("IniciarChat");
        }
        // CERRAR STREAMS Y SOCKETS
        flujoSalida.close();
        clienteConectado.close();
        servidorSSL.close();
    }
}