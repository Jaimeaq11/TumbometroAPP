package daw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptar {
    
    public static String encriptar(String password) {
        
        String pwd = "e2&b2" + password + "/sd33%";
        String passDigest = null;
        MessageDigest md;
                
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            
            for(byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            
            passDigest = sb.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            System.getLogger(Encriptar.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return passDigest;
    }
}