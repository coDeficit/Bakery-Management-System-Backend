
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//base controller for all controllers
public class SuperController {
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet = null;
    
    public void setPreparedStatement(String query) throws SQLException {
        this.preparedStatement = com.connexion.Connexion.isConnected().prepareStatement(query);
    }
    
    public void setCreateStatement() throws SQLException {
        this.statement = com.connexion.Connexion.isConnected().createStatement();
    }
    
    public void close() throws SQLException {
        
    }
}
