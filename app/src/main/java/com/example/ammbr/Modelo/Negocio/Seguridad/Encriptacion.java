/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ammbr.Modelo.Negocio.Seguridad;

import static android.util.Base64.DEFAULT;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

/**
 *
 * @author Nes Ch
 */
public class Encriptacion {

    //Codificacion con MD5
    public String encode(String secretKey, String contraseña) {
        String encoded = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(secretKey.getBytes("utf-8"));
            byte[] BytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(BytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = contraseña.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf, DEFAULT);
            encoded = new String(base64Bytes);

        } catch (Exception e) {
            System.out.println("Algo salió mal");
        }
        return encoded;
    }

    public String decode(String secretKey, String CodedContrasena) {
        String decoded = "";

        try {
            byte[] message = Base64.decode(CodedContrasena.getBytes("utf-8"), DEFAULT);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md5.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            decoded = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            System.out.println("Algo salió mal");
        }

        return decoded;
    }
}
