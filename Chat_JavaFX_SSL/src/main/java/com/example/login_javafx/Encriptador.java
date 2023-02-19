package com.example.login_javafx;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encriptador {

    //Advanced Encryption Standard
    //128 bit
    byte[] decodedKey = Base64.getDecoder().decode("MTIzNDU2Nzg5MTIzNDU2Nw==");

    public String encriptar(String input, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(secretKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(decodedKey));
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }


    public String desencriptar(String cipherText, byte[] secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(secretKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(decodedKey));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public byte[] stringToByteArray(String keyString) {
        String[] keyFragments = keyString.split(" ");

        byte[] key = new byte[16];
        for (int i = 0; i < keyFragments.length; i++) {
            key[i] = Byte.parseByte(keyFragments[i]);
        }
        return key;
    }

    public static void main(String[] args) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Encriptador encriptador = new Encriptador();

        byte[] encryptionKey = {65, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12 };

        String stringKey = "65 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12";


        byte[] key = encriptador.stringToByteArray(stringKey);

        String input = "Secret";

        System.out.println(encriptador.encriptar(input, key));
        //output: VyEcl0pLeqQLemGONcik0w==
        System.out.println(encriptador.desencriptar("VyEcl0pLeqQLemGONcik0w==", key));
    }
}