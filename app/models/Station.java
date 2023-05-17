package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Station extends Model
{
    public String name;
    public double latitude;
    public double longitude;

    public double minTemp;
    public double maxTemp;
    public double minWind;
    public double maxWind;
    public double minPressure;
    public double maxPressure;



    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name)
    {
        this.name = name;
    }

    public Station(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Reading getLastReading() {
        Reading lastReading;
        if(readings.size() > 0) {
            lastReading = readings.get(readings.size() - 1);
        } else {
            lastReading = new Reading(); // generates an empty reading to act as a placeholder
        }
        return lastReading;
    }
}
