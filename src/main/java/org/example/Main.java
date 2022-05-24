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
        post("/", (req, res) -> {
            task.add(new Task(1,"", "", false));
            return new ModelAndView(model, "");
        }, new HandlebarsTemplateEngine());
    }
}