package org.example.database;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

public class DbImpl extends DB{
    @Override
    public Connection connect() {
        try{
            //Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo", "terry", "Postgres4041*");

            String connectionString = "jdbc:postgresql://ec2-3-231-82-226.compute-1.amazonaws.com:5432/d1lqrgllhefrrf";
            return new Sql2o(connectionString, "ynelxdfbkjatnz","4352c66fe771f8acf91a91e768fed4f6fb47a8348139b994ed676c19373f9e2f").open();
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
