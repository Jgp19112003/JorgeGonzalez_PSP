import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        InetAddress dirIP_B;
        Integer puerto_B;
        try {
            System.out.println("Bienvenido al proceso A.\n" +
                    "Introduzca el nombre o direccion IP del receptor B");
            String direccionB = br.readLine();
            if (direccionB.length() == 0)
                direccionB = "localhost";
            System.out.println("Introduzca el puerto de escucha de B");
            String puertoB = br.readLine();
            if (puertoB.length() == 0)
                puertoB = "3001";
            System.out.println("Introduzca una linea de texto que enviar a B");
            String mensaje = br.readLine( );
            dirIP_B = InetAddress.getByName(direccionB);
            puerto_B = Integer.parseInt (puertoB);
            Socket socketDatosA = new Socket (direccionB, puerto_B);
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socketDatosA.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(socketDatosA.getInputStream()));
            output.println(mensaje);
            output.flush();

            String respuesta = input.readLine();
            System.out.println("Respuesta de B: " + respuesta);
            socketDatosA.close();
        }
        catch (Exception ex) {
            ex.printStackTrace( );
        }
    }
}
