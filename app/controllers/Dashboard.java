package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Member;
import models.Station;
import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
  public static void index()
  {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations = member.stations;
    render ("dashboard.html", stations);
  }

  public static void deleteStation (Long id)
  {
    Logger.info("Deleting a Station");
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    redirect ("/dashboard");
  }

  public static void addStation (String name, double latitude, double longitude)
  {
    Logger.info("Adding a Station");
    Member member = Accounts.getLoggedInMember();
    Station station = new Station (name, latitude, longitude);
    member.stations.add(station);
    member.save();

    redirect ("/dashboard");
  }
}