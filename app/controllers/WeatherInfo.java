package controllers;

import play.*;
import play.mvc.*;

public class WeatherInfo extends Controller {
    // renders the weather info page
    public static void index() {
        Logger.info("Rendering weather info");
        render("weatherinfo.html");
    }
}
