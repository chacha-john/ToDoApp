package org.example.utils;

import org.example.models.User;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class RouterUtil {
    protected static void checkLogin(Request req, Response res) {
        if(req.session().attribute("user") == null) {
            res.redirect("/login");
            halt();
        }

        User user = req.session().attribute("user");
        user.setActiveUser(user);
    }
}
