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

        Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-3-231-82-226.compute-1.amazonaws.com:5432/d1lqrgllhefrrf", "ynelxdfbkjatnz", "4352c66fe771f8acf91a91e768fed4f6fb47a8348139b994ed676c19373f9e2f");
        SqlTask task = new SqlTask(sql2o);
        post("/login", (req, res) -> {
            String email = req.queryParams("email");
            String password = req.queryParams("password");
            User user = new UserDao().login(connection,email,password);
            User.setActiveUser(user);
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
            if(request.session().attribute("user") != null) {
                model.put("userExists", true);
            }
            model.put("tasks", task.getAll(user.getId()));
            return new ModelAndView(model, "index.html");
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
            if(req.session().attribute("user") != null) {
                model.put("userExists", true);
            }
            return new ModelAndView(model, "addtask.hbs");
        }, new HandlebarsTemplateEngine());

        post("/user/task", (req, res) -> {
            checkLogin(req,res);
            Map<String, Object> model = new HashMap<>();
            User user = User.getActiveUser();

            Task newTask = new Task(user.getId(), req.queryParams("name"), req.queryParams("details"));
            task.add(newTask);
            model.put("tasks", task.getAll(user.getId()));
            res.redirect("/");
            return null;
//            return new ModelAndView(model, "index.html");
        }, new HandlebarsTemplateEngine());


        //update task
        get("/tasks/:id/edit", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            int intOfTaskTofind = Integer.parseInt(req.params("id"));
            Task editTask = task.findById(intOfTaskTofind);
            model.put("editTask", editTask);
            return new ModelAndView(model, "index.html");
        }, new HandlebarsTemplateEngine());

        post("/tasks/:id/edit", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            task.update(Integer.parseInt(req.params("id")), req.queryParams("name"), req.queryParams("details"));
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //delete one task
        get("/tasks/:id/delete", (req, res) -> {
            checkLogin(req, res);
            Map<String, Object> model = new HashMap<>();
            task.deleteById(Integer.parseInt(req.params("id")));
            res.redirect("/");
            return null;
        });


        //logout user
        get("/logout", (request, response) -> {
            checkLogin(request, response);
            request.session().removeAttribute("user");
            response.redirect("/login");
            return null;
        });

    }
}
