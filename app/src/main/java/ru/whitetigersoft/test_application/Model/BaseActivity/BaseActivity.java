package ru.whitetigersoft.test_application.Model.BaseActivity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.whitetigersoft.test_application.R;

/**
 * Created by denis on 06/07/17.
 */

public class BaseActivity extends AppCompatActivity {

    private View view;
    private TextView textView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        view = getLayoutInflater().inflate(R.layout.progress_bar_layout, null);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        setCustomToolbarView();
        this.addContentView(view, params);
    }

    private void setCustomToolbarView() {
        View titleView = getLayoutInflater().inflate(R.layout.toolbar_title_view, null);
        textView = (TextView) titleView.findViewById(R.id.text_view_toolbar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.addView(textView);
    }

    public void showProgressBar() {
        view.bringToFront();
        view.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        view.setVisibility(View.GONE);
    }



    @Override
    public void setTitle(CharSequence title) {
        try {
            super.setTitle("");
            textView.setText(title);
        } catch (Exception e) {
            super.setTitle(title);
        }
    }
}
