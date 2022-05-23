package org.example.models;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class Task {
    private int id;
    private int userId;
    private String name;
    private String description;
    private long createdon;
    private Boolean completed;

    //getters
    public int getId() {return id;}
    public int getUserId() {return userId;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public long getCreatedon() {return createdon;}
    public Boolean getCompleted() {return completed;}
    public String getFormattedCreatedOn() {
        return new SimpleDateFormat("MM/dd/yyyy @ K:mm a").format(createdon);
    }

    //setters
    public void setId(int id) {this.id = id;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setName(String name) {this.name = name;}
    public void setDescription(String description) {this.description = description;}
    public void setCreatedon(long createdon) {this.createdon = createdon;}
    public void setCompleted(Boolean completed) {this.completed = completed;}
    public void setFormattedCreatedOn() {String formattedCreatedOn = new SimpleDateFormat("MM/dd/yyyy @ K:mm a").format(this.createdon);}

    //constructor
    public Task(int userId, String name, String description, Boolean completed) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.completed = false;
        this.createdon = System.currentTimeMillis();
        setFormattedCreatedOn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                userId == task.userId &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(completed, task.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, description, completed);
    }
}
