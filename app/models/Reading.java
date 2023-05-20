package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Reading extends Model {
    public String date;
    public int code;
    public float temperature;
    public float windSpeed;
    public int windDirection;
    public int pressure;
    public String weather;
    public String weatherIcon;

    // placeholder Reading constructor
    public Reading() {
        this.date = LocalDateTime.now().toString();
        setWeather();
    }

    // main constructor for Reading class
    public Reading(String date, int code, float temperature, float windSpeed, int windDirection, int pressure) {
        this.date = date;
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        setWeather();
    }

    // sets weather condition & icon
    public void setWeather() {
        if (code == 100) {
            weather = "Clear";
            weatherIcon = "fa fa-sun";
        } else if (code == 200) {
            weather = "Partial Clouds";
            weatherIcon = "fa fa-cloud-sun";
        } else if (code == 300) {
            weather = "Cloudy";
            weatherIcon = "fa fa-cloud";
        } else if (code == 400) {
            weather = "Light Showers";
            weatherIcon = "fa fa-cloud-sun-rain";
        } else if (code == 500) {
            weather = "Heavy Showers";
            weatherIcon = "fa fa-cloud-showers-heavy";
        } else if (code == 600) {
            weather = "Rain";
            weatherIcon = "fa fa-cloud-rain";
        } else if (code == 700) {
            weather = "Snow";
            weatherIcon = "fa fa-snowflake";
        } else if (code == 800) {
            weather = "Thunder";
            weatherIcon = "fa fa-bolt";
        } else {
            weather = "No valid reading entered";
            weatherIcon = "";
        }
    }

    // converts Celsius to Fahrenheit
    public double getFahrenheit() {
        double fahrenheitTemp = (Math.round((temperature * 9 / 5 + 32) * 100)) / 100;
        return fahrenheitTemp;
    }

    // formats all data values in database to suitable String format for outputting
    public String getDateTimeFormatted() {
        if (date.charAt(0) == 'I') {
            String newstring = date.substring(8, date.length() - 5);
            LocalDateTime datetime = LocalDateTime.parse(newstring);
            newstring = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return newstring;
        } else {
            LocalDateTime datetime = LocalDateTime.parse(date);
            String newstring = datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return newstring;
        }
    }

    // getter for weather condition
    public String getWeather() {
        return weather;
    }

    // getter for weather icons
    public String getWeatherIcon() {
        return weatherIcon;
    }

    // getter for Beaufort wind scale
    public String getBeaufort() {
        if (windSpeed < 1) {
            return "Calm: 0";
        } else if (windSpeed >= 1 && windSpeed < 6) {
            return "Light Air: 1";
        } else if (windSpeed >= 6 && windSpeed < 12) {
            return "Light Breeze: 2";
        } else if (windSpeed >= 12 && windSpeed < 20) {
            return "Gentle Breeze: 3";
        } else if (windSpeed >= 20 && windSpeed < 29) {
            return "Moderate Breeze: 4";
        } else if (windSpeed >= 29 && windSpeed < 39) {
            return "Fresh Breeze: 5";
        } else if (windSpeed >= 39 && windSpeed < 50) {
            return "Strong Breeze: 6";
        } else if (windSpeed >= 50 && windSpeed < 62) {
            return "Near Gale: 7";
        } else if (windSpeed >= 62 && windSpeed < 75) {
            return "Gale: 8";
        } else if (windSpeed >= 75 && windSpeed < 89) {
            return "Severe Gale: 9";
        } else if (windSpeed >= 89 && windSpeed < 103) {
            return "Strong Storm: 10";
        } else if (windSpeed >= 103 && windSpeed < 118) {
            return "Violent Storm: 11";
        } else {
            return "Hurricane: 12";
        }
    }

    // getter for wind compass direction
    public String getWindCompass() {
        if (windDirection < 0) {
            return "Invalid direction";
        } else if (windDirection < 11.25) {
            return "North";
        } else if (windDirection < 33.75) {
            return "North North East";
        } else if (windDirection < 56.25) {
            return "North East";
        } else if (windDirection < 78.75) {
            return "East North East";
        } else if (windDirection < 101.25) {
            return "East";
        } else if (windDirection < 123.75) {
            return "East South East";
        } else if (windDirection < 146.25) {
            return "South East";
        } else if (windDirection < 168.75) {
            return "South South East";
        } else if (windDirection < 191.25) {
            return "South";
        } else if (windDirection < 213.75) {
            return "South South West";
        } else if (windDirection < 236.25) {
            return "South West";
        } else if (windDirection < 258.75) {
            return "West South West";
        } else if (windDirection < 281.25) {
            return "West";
        } else if (windDirection < 303.75) {
            return "West North West";
        } else if (windDirection < 326.25) {
            return "North West";
        } else if (windDirection < 348.75) {
            return "North North West";
        } else if (windDirection <= 360) {
            return "North";
        } else {
            return "Invalid direction";
        }
    }

    // getter for windchill index
    public double getWindChillIndex() {
        double windChillIndex = 13.12 + (0.6215 * temperature) - (11.37 * Math.pow(windSpeed, 0.16)) + (0.3965 * temperature * Math.pow(windSpeed, 0.16));
        return windChillIndex;
    }

    // getter for real feel
    public String getRealFeel() {
        String formattedRealFeel = String.format("%.2f", getWindChillIndex());
        return formattedRealFeel;
    }

    // updates the minimum and maximum values for the station (station parameter)
    public void updateStationMinMax(Station station) {
        if (station.minWind > windSpeed) {
            station.minWind = windSpeed;
        }
        if (station.maxWind < windSpeed) {
            station.maxWind = windSpeed;
        }
        if (station.minTemp > temperature) {
            station.minTemp = temperature;
        }
        if (station.maxTemp < temperature) {
            station.maxTemp = temperature;
        }
        if (station.minPressure > pressure) {
            station.minPressure = pressure;
        }
        if (station.maxPressure < pressure) {
            station.maxPressure = pressure;
        }
    }
}