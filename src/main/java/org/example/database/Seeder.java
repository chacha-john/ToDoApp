package org.example.database;

import org.sql2o.Connection;

public class Seeder {
    public static void seed(Connection connection){
        try {
            String users = "CREATE TABLE IF NOT EXISTS users( id SERIAL PRIMARY KEY, " +
                    "name VARCHAR, phone VARCHAR, email VARCHAR, password VARCHAR)";

            String tasks = "CREATE TABLE IF NOT EXISTS tasks(id SERIAL PRIMARY KEY, userid INT, name VARCHAR, " +
                    "description VARCHAR, createdon timestamp, completed BOOLEAN)";

            connection.createQuery(users).executeUpdate();
            connection.createQuery(tasks).executeUpdate();

        } catch (Exception ex){
            throw new RuntimeException("Unable to create tables", ex);
        }
    }

    public static void drop(Connection connection){
        try{
            connection.createQuery("DROP TABLE users").executeUpdate();
            connection.createQuery("DROP TABLE tasks").executeUpdate();
        } catch (Exception ex){
            throw new RuntimeException("Unable to drop tables", ex);
        }
    }
}
