package ru.whitetigersoft.test_application.LoginGroup;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ru.whitetigersoft.test_application.Model.BaseFragment.BaseFragment;
import ru.whitetigersoft.test_application.R;

/**
 * Created by denis on 06/07/17.
 */

public class StartFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.start_fragment, container, false);
        setTitle(getString(R.string.title_authorization));
        prepareViews(rootView);
        return rootView;
    }

    private void prepareViews(View rootView) {
        Button buttonGoToLogin = (Button) rootView.findViewById(R.id.button_offer_login);
        buttonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_login, new LoginFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(this.getClass().getName()).commit();
            }
        });
    }
}
