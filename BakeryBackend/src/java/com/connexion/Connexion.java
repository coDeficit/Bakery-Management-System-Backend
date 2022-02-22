
package com.connexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author CODEFICIT
 */
public class Connexion {
    private static Connection conex = null;
    private String url = "jdbc:postgresql://localhost:5432/bakery_ms_db";
    private String username = "postgres";
    private String password = "uoiea54321";

    private Connexion() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println("Driver error : " + e.getMessage());
        }

        try {
            conex = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Connection error : " + e.getMessage());
        }
    }
    
    public static Connection seconnecter() {
        if (conex == null) {
            Connexion connexion = new Connexion();
        }
        return conex;
    }
}
