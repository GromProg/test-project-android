package ru.whitetigersoft.test_application.Model.Api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ru.whitetigersoft.test_application.BuildConfig;
import ru.whitetigersoft.test_application.Model.Api.ApiListeners.ApiListener;
/**
 * Created by denis on 06/07/17.
 */

public class BaseApi {

    static final int REQUEST_TYPE_GET = 0;
    private static final int REQUEST_TYPE_POST = 1;
    static final String WEATHER_CONTROLLER = "data/2.5/";
    static final String GET_WEATHER_FORECAST = "weather?q=moscow&APPID=386c84b131be0029376af7750c7cf17e&units=Metric";

    void sendRequest(int requestType, String apiController, String apiAction, RequestParams params, ApiListener listener) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(20000);
        if (requestType == REQUEST_TYPE_GET) {
            client.get(getApiUrl() + apiController + apiAction, params, prepareResponseHandler(listener));
        }

        if (requestType == REQUEST_TYPE_POST) {
            client.post(getApiUrl() + apiController + apiAction, params, prepareResponseHandler(listener));
        }
    }

    private String getApiUrl() {
        return BuildConfig.ADDRESS_API;
    }


    private JsonHttpResponseHandler prepareResponseHandler(final ApiListener listener) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Object data = null;
                data = response;
                if (data.getClass().equals(JSONObject.class)) {
                    try {
                        if (((JSONObject) data).length() == 0) {
                            listener.onSuccess();
                        }
                        listener.onSuccess((JSONObject) data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure(e.toString());
                    }
                } else if (data.getClass().equals(JSONArray.class)) {
                    try {
                        if (((JSONArray) data).length() == 0) {
                            listener.onSuccess();
                        }
                        listener.onSuccess((JSONArray) data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure(e.toString());
                    }
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String
                    responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                listener.onFailure(responseString);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable
                    throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onFailure(throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable
                    throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onFailure(throwable.getMessage());
            }
        };
    }
}
