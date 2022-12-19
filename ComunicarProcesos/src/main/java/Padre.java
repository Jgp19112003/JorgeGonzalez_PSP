import java.io.*;

public class Padre {

    public static void main(String[] args) {
        String opc;

        try {

// Llamar a la clase Hijo compilada anteriormente


            Process hijo = new ProcessBuilder("java","C:\\Users\\Usuario DAM2\\Documents\\GitHub\\JorgeGonzalez_AD\\T1\\ComunicarProcesos\\src\\main\\java\\Hijo.java").start();
// Buffer para datos del proceso hijo


            BufferedReader br = new BufferedReader(new InputStreamReader(hijo.getInputStream()));

// Stream salida

            PrintStream ps = new PrintStream(hijo.getOutputStream(), true);

// Buffer que lee de consola

            BufferedReader in = new BufferedReader(new InputStreamReader (System. in));

            System.out.println("Introduzca mensaje: ");

// Enviar mensaje al hijo

            opc = in.readLine();

            ps.println(opc);

            System.out.println(br.readLine());

        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }

    }
}
