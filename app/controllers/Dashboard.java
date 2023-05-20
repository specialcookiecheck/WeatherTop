package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller {
    // renders the dashboard
    public static void index() {
        Logger.info("Rendering Dashboard");
        Member member = Accounts.getLoggedInMember();
        List<Station> stations = member.stations;

        // the lines below sort the station list alphabetically
        Collections.sort(stations, new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.name.toUpperCase().compareTo(s2.name.toUpperCase());
            }
        });

        // sets weather characteristics if they don't yet exist for a station
        for (Station station : stations) {
            if (station.maxPressure == 0) {
                station.setStationMinMax();
                station.setTrends();
                station.getLastReading().setWeather();
                station.save();
            }
        }
        render("dashboard.html", stations);
    }

    // deletes a station
    public static void deleteStation(Long id) {
        Logger.info("Deleting a Station");
        Member member = Accounts.getLoggedInMember();
        Station station = Station.findById(id);
        member.stations.remove(station);
        member.save();
        station.delete();
        redirect("/dashboard");
    }

    // adds a station
    public static void addStation(String name, double latitude, double longitude) {
        Logger.info("Adding a Station");
        Member member = Accounts.getLoggedInMember();
        Station station = new Station(name, latitude, longitude);
        member.stations.add(station);
        member.save();
        redirect("/dashboard");
    }
}