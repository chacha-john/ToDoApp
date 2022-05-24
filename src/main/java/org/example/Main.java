package org.example;
import static spark.Spark.*;

import org.example.dao.SqlTask;
import org.example.database.DB;
import org.example.database.DbImpl;
import org.example.database.Seeder;
import org.example.models.User;
import org.example.utils.Router;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.ModelAndView;
import java.util.HashMap;
import java.util.Map;
import org.example.models.Task;


public class Main {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());
        DB db = new DbImpl();
        Connection con = db.connect();
        Seeder.seed(con);
        Router.run(con);

    }

}