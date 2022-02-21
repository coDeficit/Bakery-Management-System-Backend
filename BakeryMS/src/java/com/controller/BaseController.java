package com.controller;

import com.models.BaseModel;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.PathParam;

public class BaseController {
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    
    public void setPreparedStatement(String query) throws SQLException, URISyntaxException {
        this.preparedStatement = com.connexion.Connexion.seconnecter().prepareStatement(query);
    }
    
    public void createStatement() throws SQLException, URISyntaxException {
        this.statement = com.connexion.Connexion.seconnecter().createStatement();
    }
    
    public void close() throws SQLException {
//        resultSet.close();
//        statement.close();
//        preparedStatement.close();
    }
}
