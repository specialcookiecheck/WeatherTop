package controllers;

import play.*;
import play.mvc.*;

public class WeatherInfo extends Controller
{
    public static void index() {
        Logger.info("Rendering weather FAQ");
        render ("weatherinfo.html");
    }
}
