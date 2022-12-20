import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Servidor {

    public static void main(String[] args) {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        Integer puerto_B;
        String mensaje = "";
        try {
            System.out.println("Bienvenido al proceso B.\n" +
                    "Introduzca el puerto en el que aceptar conexiones");
            String puertoB = br.readLine();
            if (puertoB.length() == 0)
                puertoB = "3001";
            puerto_B = Integer.parseInt (puertoB);
            ServerSocket socketAceptador = new ServerSocket (puerto_B);
            while(!mensaje.equals("finalizar B"))
            {
                System.out.println("Aceptando peticiones");
                Socket socketDatosB = socketAceptador.accept();
                System.out.println("Dando servicio a A");

                PrintWriter output = new PrintWriter(new OutputStreamWriter(socketDatosB.getOutputStream()));
                BufferedReader input = new BufferedReader(new InputStreamReader(socketDatosB.getInputStream()));

                mensaje = input.readLine();
                Date marca_temporal = new Date();
                String marca = "Momento de Recepción = " + marca_temporal.toString() + ", datos = ";
                String respuesta = marca + mensaje;

                output.println(respuesta);
                output.flush();
                socketDatosB.close();
            }
            socketAceptador.close();
            System.out.println("Finalizando la aceptación de peticiones");
        }
        catch (Exception ex) {
            ex.printStackTrace( );
        }
    }
}
