package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Reading extends Model
{
    public String date;
    public int code;
    public float temperature;
    public float windSpeed;
    public int windDirection;
    public int pressure;

    public Reading() {
        this.date = LocalDateTime.now().toString();
    }
    public Reading(String date, int code, float temperature, float windSpeed, int windDirection, int pressure) {
        this.date = date;
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
    }

    public String getWeather() {
        String weather;
        if (code == 100) {
            weather = "Clear";
        } else if (code == 200) {
            weather = "Partial Clouds";
        } else if (code == 300) {
            weather = "Cloudy";
        } else if (code == 400) {
            weather = "Light Showers";
        } else if (code == 500) {
            weather = "Heavy Showers";
        } else if (code == 600) {
            weather = "Rain";
        } else if (code == 700) {
            weather = "Snow";
        } else if (code == 800) {
            weather = "Thunder";
        } else {
            return "No valid reading entered";
        }
        return weather;
    }

    public double getFahrenheit() {
        double fahrenheitTemp = (Math.round((temperature * 9/5 + 32) * 100)) / 100;
        return fahrenheitTemp;
    }

    public String getDateTimeFormatted() {
        if(date.charAt(0) == 'I') {
            String newstring = date.substring(8, date.length()-5);
            //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime datetime = LocalDateTime.parse(newstring);
            newstring = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return newstring;
        } else {
            LocalDateTime datetime = LocalDateTime.parse(date);
            String newstring = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return newstring;
        }
    }

    public String getBeaufort() {
        if (windSpeed < 1) {
            return "Calm - 0";
        } else if (windSpeed >= 1 && windSpeed < 6) {
            return "Light Air - 1";
        } else if (windSpeed >= 6 && windSpeed < 12) {
            return "Light Breeze - 2";
        } else if (windSpeed >= 12 && windSpeed < 20) {
            return "Gentle Breeze - 3";
        } else if (windSpeed >= 20 && windSpeed < 29) {
            return "Moderate Breeze - 4";
        } else if (windSpeed >= 29 && windSpeed < 39) {
            return "Fresh Breeze - 5";
        } else if (windSpeed >= 39 && windSpeed < 50) {
            return "Strong Breeze - 6";
        } else if (windSpeed >= 50 && windSpeed < 62) {
            return "Near Gale - 7";
        } else if (windSpeed >= 62 && windSpeed < 75) {
            return "Gale - 8";
        } else if (windSpeed >= 75 && windSpeed < 89) {
            return "Severe Gale - 9";
        } else if (windSpeed >= 89 && windSpeed < 103) {
            return "Strong Storm - 10";
        } else if (windSpeed >= 103 && windSpeed < 118) {
            return "Violent Storm - 11";
        } else {
            return "Hurricane - 12";
        }
    }
}