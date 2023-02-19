package com.example.login_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import javax.net.ssl.*;

public class Cliente extends Application {
    public static void main(String[] args) throws Exception {
        String Host = "localhost";
        int puerto = 5556;//puerto remoto

        System.setProperty("javax.net.ssl.trustStore","C:\\Users\\jgp19\\Desktop\\GitHub\\JorgeGonzalez_PSP\\Login_JavaFX\\src\\main\\java\\com\\example\\login_javafx\\AlmacenSrv");
        System.setProperty("javax.net.ssl.trustStorePassword","1234567");

        System.out.println("PROGRAMA CLIENTE INICIADO....");

        SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket Cliente  = (SSLSocket) sfact.createSocket(Host, puerto);

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

        // EL servidor ME ENVIA UN MENSAJE
        if (flujoEntrada.readUTF().equalsIgnoreCase("IniciarChat")){
            launch();
        } else {
            System.out.println("El servidor a rechazado la orden de iniciar el chat");
        }

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Cliente.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}