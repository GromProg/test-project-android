package ru.whitetigersoft.test_application.Model.BaseFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import ru.whitetigersoft.test_application.R;

/**
 * Created by denis on 06/07/17.
 */

public class BaseFragment extends Fragment {

    private View progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (progressBar == null) {
            progressBar = inflater.inflate(R.layout.progress_bar_layout, null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            progressBar.setLayoutParams(params);
            progressBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            container.addView(progressBar);
        }
        return null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideKeyboard();
    }

    public void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    protected void setupKeyboardHiding(final View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (!(view instanceof Button)) {
                        hideKeyboard();
                    }
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupKeyboardHiding(innerView);
            }
        }
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.bringToFront();
            progressBar.requestLayout();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        hideProgressBar();
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideProgressBar();
    }
}
