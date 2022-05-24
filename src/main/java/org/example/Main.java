package org.example;

import org.example.models.Dao.SqlTask;
import org.example.models.Task;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo", null, null);
        SqlTask task = new SqlTask(sql2o);
        Map<String, Object> model = new HashMap<>();

        //create new task
        get("/user/:userId/task/new", (req, res) -> {
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        post("/user/:userId/task", (req, res) -> {
            int userId = Integer.parseInt(req.params("userId"));
            Task newTask = new Task(userId, req.queryParams("name"), req.queryParams("details"), false);
            newTask.setUserId(userId);
            newTask.setCreatedon();
            newTask.setFormattedCreatedOn();
            task.add(newTask);
            model.put("tasks", task.getAll(userId));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());


        //read all tasks
        get("/user/:userId", (req, res) -> {
            model.put("tasks", task.getAll(Integer.parseInt(req.params("userId"))));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        //read task details
        get("/user/:userId/tasks/:id", (req, res) -> {
            model.put("task", task.findById(Integer.parseInt(req.params("id"))));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());


        //update task
        get("/user/:userId/tasks/:id/edit", (req, res) -> {
            model.put("editTask", true);
            model.put("task", task.findById(Integer.parseInt(req.params("id"))));
            model.put("tasks", task.getAll(Integer.parseInt(req.params(":userId"))));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        post("/user/:userId/tasks/:id", (req, res) -> {
            task.update(Integer.parseInt(req.params(":id")), req.queryParams("name"), req.queryParams("details"));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());


        //delete one task
        get("/user/:userId/tasks/:id/delete", (req, res) -> {
            task.deleteById(Integer.parseInt(req.params("id")));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        //delete all tasks
        get("/user/:userId/tasks/delete", (req, res) -> {
            task.clearAllTasks();
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());
    }
}