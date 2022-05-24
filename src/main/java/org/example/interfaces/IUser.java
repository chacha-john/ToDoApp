package org.example.interfaces;

import org.example.models.User;
import org.sql2o.Connection;

public interface IUser {
    //create a user account
    void createAccount(Connection connection, User user);

    //login to an existing account
    User login(Connection connection, String email, String password);

    //change password
    boolean changePassword(Connection connection, String email, String password);
}
