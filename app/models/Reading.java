package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Reading extends Model
{
    public String location;
    public String weather;
    public int windspeed;

    public Reading(String location, String weather, int windspeed)
    {
        this.location = location;
        this.weather = weather;
        this.windspeed = windspeed;
    }
}