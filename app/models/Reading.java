package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import java.time.LocalDateTime;

@Entity
public class Reading extends Model
{
    public String dateTime;
    public int code;
    public float temperature;
    public float windSpeed;
    public int windDirection;
    public int pressure;

    public Reading(String dateTime, int code, float temperature, float windSpeed, int windDirection, int pressure) {
        this.dateTime = dateTime;
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
    }
}