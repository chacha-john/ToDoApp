package org.example.dao;

import org.example.interfaces.IUser;
import org.example.models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class UserDao implements IUser {

    @Override
    public boolean createAccount(Connection connection, User user) {
        try{
            String query = "INSERT INTO users (name,email,phone,password) VALUES(:name,:email,:phone,:password)";
             return connection.createQuery(query)
                     .bind(user)
                     .executeUpdate()
                     .getResult() > 0;
        } catch (Sql2oException ex){
            throw new RuntimeException("An error was encountered",ex);
        }

    }

    @Override
    public User login(Connection connection, String email, String password) {
        try{
            String query = "SELECT * FROM users WHERE email = :email AND password = :password";
            return connection.createQuery(query)
                    .addParameter("email",email)
                    .addParameter("password",password)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(User.class);
        } catch (Sql2oException ex){
            throw new RuntimeException("An error was encountered", ex);
        }

    }

    @Override
    public boolean changePassword(Connection connection, String email, String password) {
        try{
            String query = "UPDATE users SET password = :password WHERE email = :email";
            return connection.createQuery(query)
                    .addParameter("password",password)
                    .addParameter("email",email)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getResult()>0;
        } catch (Sql2oException ex){
            throw new RuntimeException("An error was encountered", ex);
        }

    }
}
