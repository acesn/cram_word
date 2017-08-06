package com.cram_word.acesn.cramwords.model;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by 805213 on 06.08.2017.
 */

public class DataProvider {

    private static final String DIR_SD = "cramWords";
    private static final String FILENAME_SD = "wordBase.json";

    private List<WordModel> mWordBase = new ArrayList<>();


    private static DataProvider mInstance;

    private List<DataUpdateListener> mListener = new ArrayList<>();

    public interface DataUpdateListener {
         void onDataUpdate(List<WordModel> wordBase);
    }

    public void regListener(DataUpdateListener listener) {
        mListener.add(listener);
    }

    private DataProvider() {

    }

    public static DataProvider getInstance () {
        if (mInstance == null) {
            mInstance = new DataProvider();
        }

        return mInstance;
    }

    public List<WordModel> getWordBase() {

        // get base from file
        String data = readFileSD();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type itemsListType = new TypeToken<List<WordModel>>() {}.getType();
        mWordBase = gson.fromJson(data, itemsListType);

        return mWordBase;
    }

    public void storeWordBase (List<WordModel> wordBase) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String jsonStr = gson.toJson(wordBase);
        writeFileSD(jsonStr);
    }

    public List<WordModel> addWord (WordModel word) {

        for(WordModel base: mWordBase) {
            if(base.mTargetWord.equals(word.mTargetWord)) {
                return mWordBase;
            }
        }
        mWordBase.add(word);
        storeWordBase(mWordBase);

        for(DataUpdateListener listener: mListener) {
            listener.onDataUpdate(mWordBase);
        }

        return mWordBase;
    }

    private void writeFileSD(String data) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        sdPath.mkdirs();
        // create object File, with path to file
        File sdFile = new File(sdPath, FILENAME_SD);

        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(data);
            // закрываем поток
            bw.flush();
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileSD() {

        String result = "";
        // check SD available
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return result;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        sdPath.mkdirs();

        // create object File, with path to file
        File sdFile = new File(sdPath, FILENAME_SD);


        try {
            // open read stream
            FileReader fr = new FileReader(sdFile);
            BufferedReader br = new BufferedReader(fr);

            // read
            if ((result = br.readLine()) != null) {
                return result;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

