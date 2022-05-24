package org.example;
import static spark.Spark.*;

import org.example.dao.UserDao;
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
import java.util.List;
import java.util.Map;

public class Main{
    public static void main(String[] args) {
        staticFileLocation("/public");

        DB db = new DbImpl();
        Connection con = db.connect();
        Seeder.seed(con);
        Router.run(con);


    }

}