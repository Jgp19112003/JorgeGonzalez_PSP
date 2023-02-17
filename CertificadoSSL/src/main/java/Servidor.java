import java.io.*;
import javax.net.ssl.*;

public class Servidor {
    public static void main(String[] arg) throws IOException {
        int puerto = 5556;

        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact
                .createServerSocket(puerto);
        SSLSocket clienteConectado = null;
        DataInputStream flujoEntrada = null;//FLUJO DE ENTRADA DE CLIENTE
        DataOutputStream flujoSalida = null;//FLUJO DE SALIDA AL CLIENTE

        for (int i = 1; i < 3; i++) {
            System.out.println("Esperando al cliente " + i);
            clienteConectado = (SSLSocket) servidorSSL.accept();
            flujoEntrada = new DataInputStream(clienteConectado.getInputStream());

            // EL CLIENTE ME ENVIA UN MENSAJE
            System.out.println("Recibiendo del CLIENTE: " + i + " \n\t"
                    + flujoEntrada.readUTF());

            flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());

            // ENVIO UN SALUDO AL CLIENTE
            flujoSalida.writeUTF("Saludos al cliente del servidor");
        }
        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        clienteConectado.close();
        servidorSSL.close();
    }
}