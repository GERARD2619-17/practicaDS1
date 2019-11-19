/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolinea.utilerias;

import java.security.MessageDigest;

/**
 *
 * @author JOSE
 */
public class Hash {

    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA = "SHA-1";
    public static String SHA224 = "SHA-224";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";

    //Message Digest
    public static String generarHash(String clave, String algoritmo) {
        String digested;
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(clave.getBytes());
            byte byteData[] = md.digest();
            digested = convertirHex(byteData);
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
        return digested;
    }

    private static String convertirHex(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(String.format("%02x", byteData[i]));
        }
        return sb.toString();
    }
}
