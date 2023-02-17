import model.Usuario;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class CifradoJava {
    private static Cipher cipher = null;

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        // Clave -> Clave en base 64
        // 1234567891234567 -> MTIzNDU2Nzg5MTIzNDU2Nw==
        // decode the base64 encoded string
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String nombre_usuario = "";
        String pass_usuario = "";
        byte[] decodedKey = Base64.getDecoder().decode("MTIzNDU2Nzg5MTIzNDU2Nw==");

        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher = Cipher.getInstance("AES");

        String password = "admin";
        byte[] textoClaro = password.getBytes("UTF8");

        cipher.init(Cipher.ENCRYPT_MODE, originalKey);
        byte[] passCifrada = cipher.doFinal(textoClaro);
        String textoPassCifrada = new String(passCifrada, "UTF8");

        cipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] passDescifrada = cipher.doFinal(passCifrada);
        String decryptedText = new String(passDescifrada, "UTF8");

        Usuario u1 = new Usuario("Jorge",textoPassCifrada);
        usuarios.add(u1);

        /*LOGIN*/
        System.out.println("Introduce tu nombre: ");
        nombre_usuario = in.next();
        System.out.println("Introduce tu contraseña: ");
        pass_usuario = in.next();

        cipher.init(Cipher.ENCRYPT_MODE, originalKey);
        byte[] passCifradaIntroducida = cipher.doFinal(pass_usuario.getBytes());
        String textoPassCifradaIntroducida = new String(passCifradaIntroducida, "UTF8");

        Usuario u2 = new Usuario(nombre_usuario, textoPassCifradaIntroducida);

        for (Usuario item: usuarios) {
            if (item.getNombre().equalsIgnoreCase(nombre_usuario)){
                if (item.getPassword().equals(textoPassCifradaIntroducida)){
                    System.out.println("Acceso Permitido");
                } else {
                    System.out.println("Acceso Denegado");
                }
            } else {
                System.out.println("Acceso Denegado");
            }
        }
        /**/
    }
}
