package org.example.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    Task testTask = setupTask();

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void getId() {
        assertEquals(1, testTask.getUserId());
    }

    @Test
    void getUserId() {
        assertEquals(1, testTask.getUserId());
    }

    @Test
    void getName() {
        assertEquals("Chores", testTask.getName());
    }

    @Test
    void getDescription() {
        assertEquals("Wash the dishes", testTask.getDescription());
    }


    @Test
    void getCompleted() {
        assertEquals(false, testTask.getCompleted());
    }


    //helper
    public Task setupTask(){
        return new Task(1, "Chores", "Wash the dishes");
    }
}