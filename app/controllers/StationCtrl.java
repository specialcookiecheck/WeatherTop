package controllers;

import java.time.LocalDateTime;
import java.util.List;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller {
    // renders the individual station page
    public static void index(Long id) {
        Station station = Station.findById(id);
        station.setMapSrc();
        station.save();
        Logger.info("Station id = " + id);
        render("station.html", station);
    }

    // deletes a reading (from the station and the database)
    public static void deleteReading(Long id, Long readingid) {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info("Removing reading" + reading.id);
        station.readings.remove(reading);
        station.setStationMinMax();
        station.save();
        reading.delete();
        redirect("/stations/" + id);
        //render("station.html", station);
    }

    // adds a reading to the station
    public void addReading(Long id, int code, float temperature, float windSpeed, int windDirection, int pressure) {
        String date = LocalDateTime.now().toString();
        Station station = Station.findById(id);
        Reading reading = new Reading(date, code, temperature, windSpeed, windDirection, pressure);
        station.readings.add(reading);
        reading.updateStationMinMax(station);
        station.setTrends();
        station.save();
        redirect("/stations/" + id);
    }
}