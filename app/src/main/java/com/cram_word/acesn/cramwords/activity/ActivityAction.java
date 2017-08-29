package com.cram_word.acesn.cramwords.activity;

import android.support.v7.widget.Toolbar;

/**
 * Created by 805213 on 26.08.2017.
 */

public interface ActivityAction {
    static int FRAGMENT_MAIN = 0;
    static int FRAGMENT_CRAM = 1;
    static int FRAGMENT_EDIT = 2;
    static int FRAGMENT_SETTINGS = 3;

    static int BUTTON_NONE = 0;
    static int BUTTON_DONE = 1;
    static int BUTTON_HELP = 2;

    void setFragment(int fragmentNum);

    Toolbar getToolbar();

    void setToolbarButton(int button);
}
