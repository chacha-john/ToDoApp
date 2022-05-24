package org.example.dao;

import org.example.database.DB;
import org.example.database.DbImpl;
import org.example.database.Seeder;
import org.example.interfaces.IUser;
import org.example.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private Connection con;
    private DB db;


    @BeforeEach
    void setUp() {
        db = new DbImpl();
        con = db.connect();
        Seeder.seed(con);
    }

    @AfterEach
    void tearDown() {
        Seeder.drop(con);
    }

    @Test
    void createAccount() {
        IUser userDao = new UserDao();
        User user = new User("chacha","chacha@riko.com","understand","020-224-2525");
        userDao.createAccount(con,user);
        assertEquals(1, user.getId());

    }

    @Test
    void login() {
        IUser userDao = new UserDao();
        User user = new User("chacha","chacha@riko.com","understand","020-224-2525");
        userDao.createAccount(con,user);
        User user1 = userDao.login(con,user.getEmail(),user.getPassword());
        assertEquals("chacha", user1.getName());
    }

    @Test
    void changePassword() {
        IUser userDao = new UserDao();
        User user = new User("chacha","chacha@riko.com","understand","020-224-2525");
        userDao.createAccount(con,user);
        assertTrue(userDao.changePassword(con, "chacha@riko.com","compartment"));
    }
}