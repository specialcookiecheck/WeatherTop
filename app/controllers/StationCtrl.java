package controllers;

import java.time.LocalDateTime;
import java.util.List;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller
{
    public static void index(Long id)
    {
        Station station = Station.findById(id);
        Logger.info ("Station id = " + id);
        render("station.html", station);
    }

    public static void deleteReading (Long id, Long readingid)
    {
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        Logger.info ("Removing reading" + reading.id);
        station.readings.remove(reading);
        station.setStationMinMax();
        station.save();
        reading.delete();
        render("station.html", station);
    }

    public void addReading(Long id, int code, float temperature, float windSpeed, int windDirection, int pressure)
    {
        String date = LocalDateTime.now().toString();
        Station station = Station.findById(id);
        Reading reading = new Reading(date, code, temperature, windSpeed, windDirection, pressure);
        reading.updateStationMinMax(station);
        station.readings.add(reading);
        station.setTrends();
        station.save();
        redirect ("/stations/" + id);
    }
}