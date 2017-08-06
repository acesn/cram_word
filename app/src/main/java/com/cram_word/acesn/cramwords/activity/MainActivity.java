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

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.fragment.MainFragment;
import com.cram_word.acesn.cramwords.model.DataProvider;
import com.cram_word.acesn.cramwords.model.WordModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataProvider.DataUpdateListener {

    private List<WordModel> mWordBase = new ArrayList<>();


    @Override
    public void onDataUpdate(List<WordModel> wordBase) {
        mWordBase.clear();
        mWordBase.addAll(wordBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v-> {
            onBackPressed();
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v-> {

            Snackbar.make(v, "It's all right", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            /*
            String[] training = {"Maintain", "Support"};
            WordModel word = new WordModel("Поддержка", training, "general");
            String[] training2 = {"Help"};
            WordModel word2 = new WordModel("Помощ", training2, "general");
            List<WordModel> wordBase = new ArrayList<WordModel>();
            wordBase.add(word);
            wordBase.add(word2);


            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String jsonStr = gson.toJson(wordBase);

            Snackbar.make(v, jsonStr, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            List<WordModel> wordBase2 = new ArrayList<WordModel>();
            Type itemsListType = new TypeToken<List<WordModel>>() {}.getType();
            wordBase2 = gson.fromJson(jsonStr, itemsListType);

            Snackbar.make(v, "size = " + wordBase2.size(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
*/
        });

        // load fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        transaction.add(R.id.fragmentContainer, fragment, "Главная");
        transaction.commit();

        // data
        DataProvider provider = DataProvider.getInstance();
        provider.regListener(this);
        List<WordModel> wordBase = provider.getWordBase();
        if(wordBase != null) {
            mWordBase.addAll(wordBase);
        }

        // forTest
        if(mWordBase.size() == 0) {
            String[] training = {"Maintain", "Support"};
            WordModel word = new WordModel("Поддержка", training, "general");
            String[] training2 = {"Help"};
            WordModel word2 = new WordModel("Помощ", training2, "general");
            //List<WordModel> wordBase = new ArrayList<WordModel>();
            mWordBase.add(word);
            mWordBase.add(word2);

            provider.storeWordBase(mWordBase);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
