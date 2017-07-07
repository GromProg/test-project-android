package ru.whitetigersoft.test_application.Model.Api;

import org.json.JSONObject;

import ru.whitetigersoft.test_application.Model.Api.ApiListeners.ApiListener;
import ru.whitetigersoft.test_application.Model.Api.ApiListeners.WeatherApiListener;
import ru.whitetigersoft.test_application.Model.Models.WeatherInfo;
import ru.whitetigersoft.test_application.Model.Utils.JsonParser;

/**
 * Created by denis on 06/07/17.
 */

public class WeatherApi extends BaseApi {

    public void getWeatherForecast(final WeatherApiListener weatherApiListener) {
        sendRequest(REQUEST_TYPE_GET, WEATHER_CONTROLLER, GET_WEATHER_FORECAST, null, new ApiListener() {
            @Override
            public void onSuccess(JSONObject object) throws Exception {
                super.onSuccess(object);
                weatherApiListener.onWeatherLoaded((WeatherInfo) JsonParser.parseEntityFromJson(object, WeatherInfo.class));
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                weatherApiListener.onFailure(error);
            }
        });
    }
}
