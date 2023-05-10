package controllers;

import java.util.List;

import models.Song;
import models.Reading;
import play.mvc.Controller;

public class Admin extends Controller
{
    public static void index() {
        List<Song> songs = Song.findAll();
        List<Reading> readings = Reading.findAll();
        render ("admin.html", songs, readings);
    }
}