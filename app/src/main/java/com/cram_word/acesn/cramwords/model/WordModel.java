package com.cram_word.acesn.cramwords.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 805213 on 05.08.2017.
 */

public class WordModel {

    public class TrainingWord {
        public String mWord;
        public int mLevel;
        public int mProgress;

        public TrainingWord(String word, int level, int progress) {
            mWord = word;
            mLevel = level;
            mProgress = progress;
        }
    }

    public String mTargetWord;
    public List<TrainingWord> mTrainingWord = new ArrayList<>();
    public String mTheme;

    public WordModel(String targetWord, ArrayList<TrainingWord> trainingWord, String theme) {
        mTargetWord = targetWord;
        mTrainingWord.addAll(trainingWord);
        mTheme = theme;
    }

    public WordModel(String targetWord, String[] trainingWord, String theme) {
        mTargetWord = targetWord;
        for(String word: trainingWord) {
            mTrainingWord.add(new TrainingWord(word, 0, 0));
        }
        mTheme = theme;
    }


}
