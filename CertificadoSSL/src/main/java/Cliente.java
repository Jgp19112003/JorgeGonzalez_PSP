import java.io.*;
import javax.net.ssl.*;

public class Cliente  {
    public static void main(String[] args) throws Exception {
        String Host = "localhost";
        int puerto = 5556;//puerto remoto

        System.out.println("PROGRAMA CLIENTE INICIADO....");

        SSLSocketFactory sfact = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket Cliente  = (SSLSocket) sfact.createSocket(Host, puerto);

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

        // ENVIO UN SALUDO AL SERVIDOR
        flujoSalida.writeUTF("Saludos al SERVIDOR DESDE EL CLIENTE");

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

        // EL servidor ME ENVIA UN MENSAJE
        System.out.println("Recibiendo del SERVIDOR: \n\t" + flujoEntrada.readUTF());

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();
    }
}