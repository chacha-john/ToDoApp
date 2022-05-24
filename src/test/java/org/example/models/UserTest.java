package org.example.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
    }

    @Test
    void getName() {
    }

    @Test
    void getEmail() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void getPhone() {
    }

    @Test
    void getActiveUser() {
        User testUser = setUpUser();
        User.setActiveUser(testUser);
        assertTrue(User.getActiveUser().equals(testUser));
    }

    //helper
    public User setUpUser(){
        User testUser = new User("Chacha", "test@example.com", "test4041*", "0712345678");
        return testUser;
    }
}