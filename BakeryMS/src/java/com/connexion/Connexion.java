
package com.connexion;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author CODEFICIT
 */
public class Connexion {
    private static Connection conex = null;
    URI dbUri;
    private String dbUrl;
    private String username;
    private String password;
    
    private Connexion() throws URISyntaxException {
        
        dbUri = new URI(System.getenv("DATABASE_URL"));
        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];
        dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            System.out.println("Driver Patch : " + dbUri.getPath());
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("Driver error : " + e.getMessage());
        }

        try {
            conex = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {
            System.out.println("Connection error : " + e.getMessage());
        }
    }
    
    public static Connection seconnecter() throws URISyntaxException {
        if (conex == null) {
            Connexion connexion = new Connexion();
        }
        return conex;
    }
}
