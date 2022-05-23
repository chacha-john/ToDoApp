package org.example.models.Interface;

import org.example.models.Task;

import java.util.List;

public interface InterfaceTask {
    //Implement CRUD

    //CREATE
    void add (Task task);

    //READ
    Task findById(int id);
    List<Task> getAll(int userId);

    //UPDATE
    void update (int id, String name, String description, Boolean completed);

    //DELETE
    void deleteById(int id);
    void clearAllTasks();
}
