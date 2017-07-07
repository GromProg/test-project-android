package ru.whitetigersoft.test_application.Model.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by denis on 07/07/17.
 */

public class WeatherInfo extends BaseModel {

    @SerializedName("main")
    private MainWeatherInfo mainWeatherInfo;

    @SerializedName("name")
    private String city;

    public MainWeatherInfo getMainWeatherInfo() {
        return mainWeatherInfo;
    }

    public String getCity() {
        return city;
    }
}
