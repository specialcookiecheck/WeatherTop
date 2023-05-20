package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

@Entity
public class Station extends Model {
    public String name;
    public double latitude;
    public double longitude;

    public double minTemp;
    public double maxTemp;
    public double minWind;
    public double maxWind;
    public double minPressure;
    public double maxPressure;

    public String tempTrend;
    public String windTrend;
    public String pressureTrend;

    public String mapSrc;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    // constructor for Station class
    public Station(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // returns the last Reading for a Station (and creates a placeholder Reading if no Readings exist)
    public Reading getLastReading() {
        Reading lastReading;
        if (readings.size() > 0) {
            lastReading = readings.get(readings.size() - 1);
        } else {
            lastReading = new Reading(); // generates an empty reading to act as a placeholder
        }
        return lastReading;
    }

    // sets the min and max weather values for a Station, and clears them if all readings have been deleted
    public void setStationMinMax() {
        Logger.info("Setting StationMinMax");

        if (readings.size() > 0) {
            for (int i = 0; i < readings.size(); i++) {
                Logger.info("Reading" + i + readings.get(i));

                Logger.info("Reading Temp: " + readings.get(i).temperature);
                Logger.info("Current minTemp: " + minTemp);
                if (minTemp > readings.get(i).temperature || minTemp == 0) {
                    minTemp = readings.get(i).temperature;
                }
                Logger.info("Current maxTemp: " + maxTemp);
                if (maxTemp < readings.get(i).temperature) {
                    maxTemp = readings.get(i).temperature;
                }

                Logger.info("Reading WindSpeed: " + readings.get(i).windSpeed);
                Logger.info("Current minWind: " + minWind);
                if (minWind > readings.get(i).windSpeed || minWind == 0) {
                    minWind = readings.get(i).windSpeed;
                }
                Logger.info("Current maxWind: " + maxWind);
                if (maxWind < readings.get(i).windSpeed) {
                    maxWind = readings.get(i).windSpeed;
                }

                Logger.info("Reading Pressure: " + readings.get(i).pressure);
                Logger.info("Current minPressure: " + minPressure);
                if (minPressure > readings.get(i).pressure || minPressure == 0) {
                    minPressure = readings.get(i).pressure;
                }
                Logger.info("Current maxPressure: " + maxPressure);
                if (maxPressure < readings.get(i).pressure) {
                    maxPressure = readings.get(i).pressure;
                }
            }
        } else {
            minTemp = 0;
            maxTemp = 0;
            minWind = 0;
            maxWind = 0;
            minPressure = 0;
            maxPressure = 0;
        }
    }

    // activates setters for all trends
    public void setTrends() {
        if (readings.size() > 2) {
            Logger.info("Setting trends");
            setTempTrend();
            setWindTrend();
            setPressureTrend();
        }
    }

    // calculates and sets the temperature trend
    private void setTempTrend() {
        Logger.info("Setting temp trend");

        Reading currentReading = readings.get(readings.size() - 1);
        Reading previousReading = readings.get(readings.size() - 2);
        Reading twoToLastReading = readings.get(readings.size() - 3);

        float currentTemp = currentReading.temperature;
        float previousTemp = previousReading.temperature;
        float twoToLastTemp = twoToLastReading.temperature;

        if (currentTemp > previousTemp && previousTemp > twoToLastTemp) {
            tempTrend = "Up";
        } else if (currentTemp < previousTemp && previousTemp < twoToLastTemp) {
            tempTrend = "Down";
        } else {
            tempTrend = "Neutral";
        }
    }

    // calculates and sets the wind trend
    private void setWindTrend() {
        Logger.info("Setting wind trend");

        Reading currentReading = readings.get(readings.size() - 1);
        Reading previousReading = readings.get(readings.size() - 2);
        Reading twoToLastReading = readings.get(readings.size() - 3);

        float currentWind = currentReading.windSpeed;
        float previousWind = previousReading.windSpeed;
        float twoToLastWind = twoToLastReading.windSpeed;

        Logger.info(String.valueOf(currentWind));
        Logger.info(String.valueOf(previousWind));
        Logger.info(String.valueOf(twoToLastWind));

        if (currentWind > previousWind && previousWind > twoToLastWind) {
            windTrend = "Up";
        } else if (currentWind < previousWind && previousWind < twoToLastWind) {
            windTrend = "Down";
        } else {
            windTrend = "Neutral";
        }
    }

    // calculates and sets the pressure trend
    private void setPressureTrend() {
        Logger.info("Setting pressure trend");

        Reading currentReading = readings.get(readings.size() - 1);
        Reading previousReading = readings.get(readings.size() - 2);
        Reading twoToLastReading = readings.get(readings.size() - 3);

        float currentPressure = currentReading.pressure;
        float previousPressure = previousReading.pressure;
        float twoToLastPressure = twoToLastReading.pressure;

        if (currentPressure > previousPressure && previousPressure > twoToLastPressure) {
            pressureTrend = "Up";
        } else if (currentPressure < previousPressure && previousPressure < twoToLastPressure) {
            pressureTrend = "Down";
        } else {
            pressureTrend = "Neutral";
        }
    }

    // returns trend icon class for temperature trend
    public String outputTempTrend() {
        if (readings.size() > 2) {
            if (tempTrend.equals("Up")) {
                return "fas fa-arrow-trend-up has-text-danger";
            } else if (tempTrend.equals("Down")) {
                return "fas fa-arrow-trend-down has-text-info";
            } else {
                return "fas fa-grip-lines";
            }
        } else {
            return "fas fa-grip-lines";
        }
    }

    // returns trend icon class for wind trend
    public String outputWindTrend() {
        if (readings.size() > 2) {
            if (windTrend.equals("Up")) {
                return "fas fa-arrow-trend-up has-text-black-bis";
            } else if (windTrend.equals("Down")) {
                return "fas fa-arrow-trend-down has-text-grey-light";
            } else {
                return "fas fa-grip-lines";
            }
        } else {
            return "fas fa-grip-lines";
        }
    }

    // returns trend icon class for pressure trend
    public String outputPressureTrend() {
        if (readings.size() > 2) {
            if (pressureTrend.equals("Up")) {
                return "fas fa-arrow-trend-up has-text-danger";
            } else if (pressureTrend.equals("Down")) {
                return "fas fa-arrow-trend-down has-text-info";
            } else {
                return "fas fa-grip-lines";
            }
        } else {
            return "fas fa-grip-lines";
        }
    }

    // sets the map src if none is present
    public void setMapSrc() {
        if (mapSrc == null) {
            mapSrc = "https://maps.google.com/maps?q=" + Double.toString(latitude) + ", " + Double.toString(longitude) + "&z=10" + "&output=embed";
        }
    }
}
