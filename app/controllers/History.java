package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class History extends Controller {
    // renders the history page
    public static void index() {
        Logger.info("Rendering history");
        render("history.html");
    }
}
