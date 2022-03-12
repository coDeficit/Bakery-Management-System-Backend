/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author CODEFICIT
 */
public class MD5 {
    public static String getMd5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(str.getBytes());
        
        BigInteger no = new BigInteger(1, messageDigest);
        String hashedtext = no.toString(16);
        while (hashedtext.length() < 32) {
            hashedtext = "0" + hashedtext;
        }
        return hashedtext;
    }
}
