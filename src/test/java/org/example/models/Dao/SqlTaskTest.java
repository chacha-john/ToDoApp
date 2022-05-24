package org.example.models.Dao;

import org.example.models.Task;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

public class SqlTaskTest {

    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo_test", null, null);
    private static Connection conn;
    private static  SqlTask task = new SqlTask(sql2o);

    Task testTask = setupTask();
    Task testTask1 = setupTask();
    Task testTask2 = setupTask();

    @BeforeEach
    void setUp() throws Exception{
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo_test", null, null);
        task = new SqlTask(sql2o);
        conn = (Connection) sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception {
        task.clearAllTasks();
        System.out.println("Deleting from database");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addingTaskSetsId() throws Exception { //passed
        int initialTaskId = testTask.getId();
        task.add(testTask);
        Assertions.assertNotEquals(initialTaskId, testTask.getId());
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception { //passed
        task.add(testTask);
        assertEquals(testTask.getId(), task.findById(testTask.getId()).getId());
    }

    @Test
    public void addedTasksAreReturnedFromGetAll() throws Exception { //passed
        task.add(testTask);
        task.add(testTask1);
        task.add(testTask2);
        assertEquals(3, task.getAll(1).size());
    }
    
    @Test
    public void noTaskReturnsEmptyList() throws Exception { //passed
       assertEquals(0, task.getAll(1).size()); 
    }

    @Test
    public void updatingTaskChangesTaskDescription() throws Exception {
        String initialDescription = "Wash the dishes";
        task.add(testTask);
        task.update(testTask.getId(), "Chores", "Mow the lawn");
        Task updatedTask = task.findById(testTask.getId());
        Assertions.assertNotEquals(initialDescription, updatedTask.getDescription());
    }

    @Test
    public void updatingTaskChangesTaskName() throws Exception {
        String initialName = "Chores";
        task.add(testTask);
        task.update(testTask.getId(), "House Chores", "Wash the dishes");
        Task updatedTask = task.findById(testTask.getId());
        Assertions.assertNotEquals(initialName, updatedTask.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectTask() throws Exception { //passed
        task.add(testTask);
        task.add(testTask1);
        task.add(testTask2);
        task.deleteById(testTask.getId());
        task.deleteById(testTask1.getId());
        assertEquals(1, task.getAll(1).size());
    }

    @Test
    public void clearAllTasksDeletesAllTasks() throws Exception { //passed
        task.add(testTask);
        task.add(testTask1);
        task.add(testTask2);
        task.clearAllTasks();
        assertEquals(0, task.getAll(1).size());
    }

    //helper
    public Task setupTask(){
        return new Task(1, "Chores", "Wash the dishes", false);
    }
}