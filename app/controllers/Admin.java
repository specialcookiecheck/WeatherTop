package controllers;

import java.util.List;

import models.Reading;
import play.mvc.Controller;

import java.time.LocalDateTime;

public class Admin extends Controller {
    // renders the admin (hidden from menu but accessible page)
    public static void index() {
        List<Reading> readings = Reading.findAll();
        render("admin.html", readings);
    }
}