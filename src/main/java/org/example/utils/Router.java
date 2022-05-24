package org.example.utils;

import org.example.dao.SqlTask;
import org.example.dao.UserDao;
import org.example.models.Task;
import org.example.models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Route;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Router extends RouterUtil {
    public static void run(Connection connection){

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/todo", "terry", "Postgres4041*");
        SqlTask task = new SqlTask(sql2o);
        post("/login", (req, res) -> {
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            User user = new UserDao().login(connection,email,password);
            if (user!=null){
                req.session().attribute("user",user);
                res.redirect("/");
            } else{
                res.redirect("/login");
            }
            return null;/*edit*/
        });

        get("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", (request, response) -> {
            checkLogin(request, response);
            Map<String, Object> model = new HashMap<>();
            User user = User.getActiveUser();
            model.put("tasks", task.getAll(user.getId()));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/register", (req, res) -> {
            return new ModelAndView(null, "signup.hbs");/*edit*/
        }, new HandlebarsTemplateEngine());

        post("/register", (request, response) -> {
            String name = request.queryParams("name");
            String email = request.queryParams("email");
            String phone = request.queryParams("phone");
            String password = request.queryParams("password");

            User user = new User(name, email, password, phone);
            UserDao userDao = new UserDao();
            userDao.createAccount(connection, user);
            response.redirect("/login");
            return null;
        });



        //create new task
        get("/user/task/new", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "addtask.hbs");
        }, new HandlebarsTemplateEngine());

        post("/user/task", (req, res) -> {
            checkLogin(req,res);
            Map<String, Object> model = new HashMap<>();
            User user = User.getActiveUser();
            Task newTask = new Task(user.getId(), req.queryParams("name"), req.queryParams("details"));
//            newTask.setCreatedon();
//            newTask.setFormattedCreatedOn();
            task.add(newTask);
            model.put("tasks", task.getAll(user.getId()));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //read all tasks
        get("/user/:userId", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            model.put("tasks", task.getAll(Integer.parseInt(req.params("userId"))));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        //read task details
        get("/user/:userId/tasks/:id", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            model.put("task", task.findById(Integer.parseInt(req.params("id"))));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());


        //update task
        get("/user/:userId/tasks/:id/edit", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            model.put("editTask", true);
            model.put("task", task.findById(Integer.parseInt(req.params("id"))));
            model.put("tasks", task.getAll(Integer.parseInt(req.params(":userId"))));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        post("/user/:userId/tasks/:id", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            task.update(Integer.parseInt(req.params(":id")), req.queryParams("name"), req.queryParams("details"));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());


        //delete one task
        get("/user/:userId/tasks/:id/delete", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            task.deleteById(Integer.parseInt(req.params("id")));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());

        //delete all tasks
        get("/user/:userId/tasks/delete", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            task.clearAllTasks();
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());
    }
}
