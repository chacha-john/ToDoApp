package org.example.database;

import org.sql2o.Connection;

public class Seeder {
    public static void seed(Connection connection){
        try {
            String users = "CREATE TABLE IF NOT EXISTS users( id SERIAL PRIMARY KEY, " +
                    "name VARCHAR, phone VARCHAR, email VARCHAR, password VARCHAR)";

            connection.createQuery(users).executeUpdate();

        } catch (Exception ex){
            throw new RuntimeException("Unable to create table", ex);
        }
    }

    public static void drop(Connection connection){
        try{
            connection.createQuery("DROP TABLE users").executeUpdate();
        } catch (Exception ex){
            throw new RuntimeException("Unable to drop table", ex);
        }
    }
}
