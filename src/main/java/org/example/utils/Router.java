package org.example.utils;

import org.example.dao.UserDao;
import org.example.models.User;
import org.sql2o.Connection;
import spark.ModelAndView;
import spark.Route;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Router extends RouterUtil {
    public static void run(Connection connection){
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
            return new ModelAndView(model, "index.hbs");

        }, new HandlebarsTemplateEngine());

        get("/register", (req, res) -> {
            return new ModelAndView(null, "signup.hbs");/*edit*/
        }, new HandlebarsTemplateEngine());
    }
}
