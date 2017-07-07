package ru.whitetigersoft.test_application.Model.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by denis on 07/07/17.
 */

public class MainWeatherInfo {

    @SerializedName("temp")
    private double averageTemperature;
    @SerializedName("max_temp")
    private double maxTemp;
    @SerializedName("min_temp")
    private double minTemp;

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
}
