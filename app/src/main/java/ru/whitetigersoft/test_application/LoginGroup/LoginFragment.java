package ru.whitetigersoft.test_application.LoginGroup;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.whitetigersoft.test_application.Model.Api.ApiListeners.WeatherApiListener;
import ru.whitetigersoft.test_application.Model.Api.WeatherApi;
import ru.whitetigersoft.test_application.Model.BaseFragment.BaseFragment;
import ru.whitetigersoft.test_application.Model.Models.WeatherInfo;
import ru.whitetigersoft.test_application.Model.Utils.StringHelper;
import ru.whitetigersoft.test_application.R;

/**
 * Created by denis on 06/07/17.
 */

public class LoginFragment extends BaseFragment {

    private TextView editTextEmail;
    private TextView editTextPassword;

    private View.OnClickListener onButtonEnterClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            attemptLogin();
        }
    };

    private void onForgerPasswordClick() {
        Snackbar.make(getView(), getResources().getString(R.string.title_forget_password), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        setTitle(getString(R.string.title_authorization));
        setupKeyboardHiding(rootView);
        prepareViews(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        editTextEmail.post(new Runnable() {
            @Override
            public void run() {
                editTextEmail.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editTextEmail, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void prepareViews(View rootView) {
        editTextEmail = (EditText) rootView.findViewById(R.id.edit_text_email);
        editTextPassword = (EditText) rootView.findViewById(R.id.edit_text_password);
        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyboard();
                    if (isResetPasswordButtonTouched(event)) {
                        onForgerPasswordClick();
                        return true;
                    }
                }
                return false;
            }
        });
        Button enterButton = (Button) rootView.findViewById(R.id.button_login);
        enterButton.setOnClickListener(onButtonEnterClicked);
    }

    private boolean isResetPasswordButtonTouched(MotionEvent event) {
        if (editTextPassword.getCompoundDrawables().length > 0) {
            return event.getRawX() >= (editTextPassword.getRight() - getRightDrawableBoundWidth());
        } else {
            return false;
        }
    }

    private int getRightDrawableBoundWidth() {
        final int DRAWABLE_RIGHT = 2;
        return editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
    }

    private void attemptLogin() {
        editTextEmail.setError(null);
        editTextPassword.setError(null);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!StringHelper.isPasswordValid(password)) {
            editTextPassword.setError(getString(R.string.short_password_error));
            focusView = editTextPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError(getString(R.string.empty_email_error));
            focusView = editTextEmail;
            cancel = true;
        } else if (!StringHelper.isEmailValid(email)) {
            editTextEmail.setError(getString(R.string.wrong_email_error));
            focusView = editTextEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return;
        }
        authenticate();
    }

    private void authenticate() {
        showProgressBar();
        hideKeyboard();
        WeatherApi weatherApi = new WeatherApi();
        weatherApi.getWeatherForecast(new WeatherApiListener() {
            @Override
            public void onWeatherLoaded(WeatherInfo weatherInfo) {
                super.onWeatherLoaded(weatherInfo);
                hideProgressBar();
                String weatherInfoString = getForecastString(weatherInfo);
                showBigSnackbar(weatherInfoString, getView());
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                hideProgressBar();
                Snackbar.make(getView(), error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showBigSnackbar(String weatherInfoString, View view) {
        Snackbar snackbar = Snackbar.make(view, weatherInfoString, Snackbar.LENGTH_SHORT).setDuration(Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(4);
        snackbar.show();
    }

    private String getForecastString(WeatherInfo weatherInfo) {
        String city = weatherInfo.getCity();
        double averageTemperature = weatherInfo.getMainWeatherInfo().getAverageTemperature();
        double maxTemperature = weatherInfo.getMainWeatherInfo().getMaxTemp();
        double minTemperature = weatherInfo.getMainWeatherInfo().getMinTemp();
        String stringTitle = "Погода в " + city + " \n";
        String stringTemp = "Средняя температура " + averageTemperature + "°C,\n";
        String stringMaxTemp = "Максимальная температура " + maxTemperature + "°C,\n";
        String stringMin = "Минимальная температура " + minTemperature + "°C";
        return stringTitle + stringTemp + stringMaxTemp + stringMin;
    }
}
