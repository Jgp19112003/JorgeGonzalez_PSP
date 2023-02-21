package com.example.login_javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class HelloController {

    @FXML
    public TextField nombre_usuario;
    @FXML
    public TextField password;
    @FXML
    public TextField texto_error;
    @FXML
    private PasswordField password_oculta;
    @FXML
    private CheckBox password_mostrar;

    File file = new File("data.txt");

    HashMap<String, String> loginInfo = new HashMap<>();

    Encriptador encriptador = new Encriptador();

    byte[] encryptionKey = {65, 12, 12, 12, 12, 12, 12, 12, 12,
            12, 12, 12, 12, 12, 12, 12};

    /*Metodo que permite al usuario mostrar contraseña*/
    @FXML
    void mostrarPassword(ActionEvent event) {
        if (password_mostrar.isSelected()) {
            password.setText(password_oculta.getText());
            password.setVisible(true);
            password_oculta.setVisible(false);
            return;
        }
        password_oculta.setText(password.getText());
        password_oculta.setVisible(true);
        password.setVisible(false);
    }

    /*Metodo que permite al usuario iniciar sesion*/
    @FXML
    void manejoInicioSesion(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String username = nombre_usuario.getText();
        String password = getPassword();
        actualizarUsuariosyPasswords();

        String encryptedPassword = loginInfo.get(username);
        try {
            if (password.equals(encriptador.desencriptar(encryptedPassword, encryptionKey))) {
                System.out.println("Inicio de sesion exitoso!");

                /*CONEXION USUARIO*/

                String Host = "localhost";
                int puerto = 5556;//puerto remoto

                System.setProperty("javax.net.ssl.trustStore", "src/main/java/com/example/login_javafx/AlmacenSrv");
                System.setProperty("javax.net.ssl.trustStorePassword", "1234567");

                System.out.println("Cliente conectandose al chat....");

                SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault();
                SSLSocket Cliente = (SSLSocket) sfact.createSocket(Host, puerto);

                DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

                DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

                if (flujoEntrada.readUTF().equalsIgnoreCase("IniciarChat")) {

                } else {
                    System.out.println("El servidor a rechazado la orden de iniciar el chat");
                }

                System.out.println("Te has conectado al chat!");
                Cliente cliente = new Cliente(Cliente, username);
                cliente.recibirMensaje();
                cliente.enviarMensaje();

                flujoEntrada.close();
                flujoSalida.close();
                Cliente.close();
                /**/
            } else {
                texto_error.setVisible(true);
            }
        } catch (RuntimeException e) {
            System.out.println("Ese usuario no esta registrado!!");
        }
    }

    /*Metodo que permite al usuario crear cuenta*/
    @FXML
    void crearCuenta(ActionEvent event) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        escribirDatos();
    }

    private String getPassword() {
        if (password.isVisible()) {
            return password.getText();
        } else {
            return password_oculta.getText();
        }
    }

    /*Metodo que actualiza los usuarios y contraseñas en el fichero*/
    private void actualizarUsuariosyPasswords() throws IOException {
        Scanner scanner = new Scanner(file);
        loginInfo.clear();
        loginInfo = new HashMap<>();
        while (scanner.hasNext()) {
            String[] splitInfo = scanner.nextLine().split(",");
            loginInfo.put(splitInfo[0], splitInfo[1]);
        }
    }

    /*Metodo que escribe los datos en el fichero*/
    private void escribirDatos() throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String nombre_usuarioText = nombre_usuario.getText();
        String password = getPassword();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        writer.write(nombre_usuarioText + "," + encriptador.encriptar(password, encryptionKey) + "\n");
        writer.close();
        System.out.println("Se ha creado la cuenta correctamente!");
    }
}