package org.example.database;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class DbImpl extends DB{
    @Override
    public Connection connect() {
        try{
            String connectionString = "jdbc:postgresql://localhost:5432/todo";
            return new Sql2o(connectionString, "riko","nzfu5321").open();
        } catch (Sql2oException ex){
            throw new RuntimeException("Program encountered an error", ex);
        }
    }

    @Override
    public void disconnect(Connection connection) {
        try {
            connection.close();
        } catch (Sql2oException ex){
            throw new RuntimeException("Program encountered an error");
        }

    }
}
