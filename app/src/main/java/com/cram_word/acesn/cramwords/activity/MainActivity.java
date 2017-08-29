package com.cram_word.acesn.cramwords.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.fragment.CramFragment;
import com.cram_word.acesn.cramwords.fragment.EditWordFragment;
import com.cram_word.acesn.cramwords.fragment.MainFragment;
import com.cram_word.acesn.cramwords.model.DataProvider;
import com.cram_word.acesn.cramwords.model.WordModel;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements DataProvider.DataUpdateListener, ActivityAction {

    public List<WordModel> mWordBase = new ArrayList<>();
    public List<String> mTheme = new ArrayList<>();
    public Toolbar mToolbar;

    private MenuItem mMenuDone;
    private MenuItem mMenuHelp;
    private Menu mMenu;

    private int mMenuButton;

    public interface FragmentInteraction {
        public static int BUTTON_BACK = 0;
        public static int BUTTON_DONE = 1;
        public static int BUTTON_HELP = 2;

        public Boolean onButtonPressed(int button);
    }

    @Override
    public void setFragment(int fragmentNum) {
        switch (fragmentNum) {
            case FRAGMENT_MAIN:
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new MainFragment();
                //transaction.add(R.id.fragmentContainer, fragment, "Главная");
                transaction.replace(R.id.fragmentContainer, fragment, "current");
                transaction.addToBackStack(null);
                transaction.commit();

                mToolbar.setNavigationIcon(R.drawable.toolbar_exit);
                break;
            }

            case FRAGMENT_CRAM:
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new CramFragment();
                //transaction.add(R.id.fragmentContainer, fragment, "Учить");
                transaction.replace(R.id.fragmentContainer, fragment, "current");
                transaction.addToBackStack(null);
                transaction.commit();

                mToolbar.setNavigationIcon(R.drawable.toolbar_back);
                break;
            }

            case FRAGMENT_EDIT:
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = new EditWordFragment();
                //transaction.add(R.id.fragmentContainer, fragment, "Редактировать");
                transaction.replace(R.id.fragmentContainer, fragment, "current");
                transaction.addToBackStack(null);
                transaction.commit();

                mToolbar.setNavigationIcon(R.drawable.toolbar_back);
                break;
            }
        }
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void setToolbarButton (int button) {

        mMenuButton = button;
        if (mMenu == null) {
            return;
        }


        if(mMenuDone != null) {
            if ((button & BUTTON_DONE) != 0) {
                mMenuDone.setVisible(true);
            } else {
                mMenuDone.setVisible(false);
            }
        }

        if(mMenuHelp != null) {
            if ((button & BUTTON_HELP) != 0) {
                mMenuHelp.setVisible(true);
            } else {
                mMenuHelp.setVisible(false);
            }
        }
    }

    @Override
    public void onDataUpdate(List<WordModel> wordBase) {
        mWordBase.clear();
        mWordBase.addAll(wordBase);

        mTheme.clear();
        for (WordModel word: mWordBase) {
            if(!mTheme.contains(word.mTheme)) {
                mTheme.add(word.mTheme);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setNavigationIcon(R.drawable.toolbar_exit);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(v-> {
            onBackPressed();
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v-> {

            Snackbar.make(v, "under constraction", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        });

        // load fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        //transaction.add(R.id.fragmentContainer, fragment, "Главная");
        transaction.replace(R.id.fragmentContainer, fragment, "current");
        //transaction.addToBackStack(null);
        transaction.commit();

        // data
        DataProvider provider = DataProvider.getInstance();
        provider.regListener(this);
        List<WordModel> wordBase = provider.getWordBase();
        if(wordBase != null) {
            mWordBase.addAll(wordBase);
        }

        // theme
        for (WordModel word: mWordBase) {
            if(!mTheme.contains(word.mTheme)) {
                mTheme.add(word.mTheme);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        mMenu = menu;

        mMenuDone = mMenu.findItem(R.id.action_done);
        //mMenuDone = findViewById(menuItem.getItemId());
        mMenuHelp = mMenu.findItem(R.id.action_help);
        //mMenuHelp = findViewById(menuItem2.getItemId());

        setToolbarButton(mMenuButton);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(fragment instanceof FragmentInteraction) {
                return ((FragmentInteraction) fragment).onButtonPressed(FragmentInteraction.BUTTON_DONE);
            }

        }

        if (id == R.id.action_help) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(fragment instanceof FragmentInteraction) {
                return ((FragmentInteraction) fragment).onButtonPressed(FragmentInteraction.BUTTON_HELP);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
