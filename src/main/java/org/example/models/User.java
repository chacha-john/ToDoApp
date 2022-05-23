package org.example.models;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;

    private static User activeUser;

    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User au) {
        User.activeUser = au;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && email.equals(user.email) && password.equals(user.password) && phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, phone);
    }
}
