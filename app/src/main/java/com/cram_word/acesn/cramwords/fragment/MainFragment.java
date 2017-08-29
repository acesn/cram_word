package com.cram_word.acesn.cramwords.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.activity.ActivityAction;
import com.cram_word.acesn.cramwords.activity.MainActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private Toolbar mToolbar;
    private ActivityAction mParent;

    public MainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        View btnCram = view.findViewById(R.id.btnStartCram);
        View btnEdit = view.findViewById(R.id.btnEdit);
        View btnSettings = view.findViewById(R.id.btnSettings);

        Activity toolHolder = getActivity();
        if(toolHolder instanceof ActivityAction) {
            Toolbar toolbar = ((ActivityAction) toolHolder).getToolbar();
            toolbar.setNavigationIcon(R.drawable.toolbar_exit);
        }

        //Activity activity = getActivity();
        // config toolbar
        if(toolHolder instanceof ActivityAction) {
            ((ActivityAction)toolHolder).setToolbarButton(ActivityAction.BUTTON_NONE);
        }

        btnCram.setOnClickListener(v -> {

            Activity activity = getActivity();
            if(activity instanceof ActivityAction) {
                ((ActivityAction) activity).setFragment(ActivityAction.FRAGMENT_CRAM);
            }
        });

        btnEdit.setOnClickListener(v -> {

            Activity activity = getActivity();
            if(activity instanceof ActivityAction) {
                ((ActivityAction) activity).setFragment(ActivityAction.FRAGMENT_EDIT);
            }
        });


        return view;
    }
}
