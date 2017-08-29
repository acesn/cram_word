package com.cram_word.acesn.cramwords.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.activity.ActivityAction;
import com.cram_word.acesn.cramwords.activity.MainActivity;
import com.cram_word.acesn.cramwords.model.WordModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CramFragment extends Fragment implements MainActivity.FragmentInteraction {

    private TextView tvTargetWord;
    private TextView tvForeignTitle;
    private EditText etForeignWord;
    private Button btnDone;
    private Button btnHelp;
    private Button btnHint;

    private int mCurrentWord;
    private List<WordModel> mWordBase = new ArrayList<>();
    private List<String> mAnswers = new ArrayList<>();
    private int mAnswerNumber;

    private RelativeLayout mView;


    public CramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CramFragment.
     */
    public static CramFragment newInstance() {
        CramFragment fragment = new CramFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cram, container, false);
        mView = (RelativeLayout)view;
        tvTargetWord = (TextView) view.findViewById(R.id.tvTargetWord);
        tvForeignTitle = (TextView) view.findViewById(R.id.tvForeignTitle);
        etForeignWord = (EditText) view.findViewById(R.id.editForeignWord1);
        etForeignWord.setInputType(InputType.TYPE_CLASS_TEXT);

        btnDone = (Button) view.findViewById(R.id.btnDone);
        btnHelp = (Button) view.findViewById(R.id.btnHelp);
        btnHint = (Button) view.findViewById(R.id.btnHint);

        Activity activity = getActivity();
        if(activity instanceof MainActivity) {
            setWordBase(((MainActivity) activity).mWordBase);
        }

        // config toolbar
        if(activity instanceof ActivityAction) {
            ((ActivityAction)activity).setToolbarButton(ActivityAction.BUTTON_DONE | ActivityAction.BUTTON_HELP);
        }

        nextWordIndex();
        fillField();
        setOnClick();

        return view;
    }

    public void setWordBase(List<WordModel> wordBase) {
        mWordBase = wordBase;
        mCurrentWord = 0;
        nextWordIndex();
        fillField();
    }

    private void setOnClick() {

        btnDone.setOnClickListener(v -> {
            String checkText = etForeignWord.getText().toString();
            WordModel word = mWordBase.get(mCurrentWord);

            int index = 0;
            for (String target: mAnswers) {
                if(checkText.toLowerCase().equals(target.toLowerCase())) {

                    etForeignWord.setTextColor(getResources().getColor(R.color.answer_waiting));
                    mAnswers.remove(index);
                    if(mAnswers.size() > 0)
                    {
                        mAnswerNumber++;
                        tvForeignTitle.setText("Перевод вариант " + Integer.toString(mAnswerNumber + 1));
                        etForeignWord.setText("");
                        etForeignWord.setHint("");
                    }
                    else {
                        nextWordIndex();

                        fillField();
                    }

                    return;
                }
                index++;
            }
            etForeignWord.setTextColor(getResources().getColor(R.color.answer_wrong));
            /*
            for (WordModel.TrainingWord target: word.mTrainingWord) {
                if(checkText.toLowerCase().equals(target.mWord.toLowerCase())) {
                    etForeignWord.setTextColor(getResources().getColor(R.color.answer_correct));
                    return;
                }
            }

            etForeignWord.setTextColor(getResources().getColor(R.color.answer_wrong));
            */
        });

        /*
        btnNext.setOnClickListener(v -> {
            mCurrentWord++;
            if(mCurrentWord >= mWordBase.size()) {
                mCurrentWord = 0;
            }

            fillField();
        });
*/
        btnHint.setOnClickListener(v-> {
            etForeignWord.setText("");
            String hint = mAnswers.get(0);
            hint = hint.substring(0, 1);
            etForeignWord.setHint(hint);
        });

        btnHelp.setOnClickListener(v-> {
            etForeignWord.setText("");
            etForeignWord.setHint(mAnswers.get(0));
        });
    }

    private void fillField() {
        if((mWordBase != null) && (mWordBase.size() > 0)) {
            WordModel word = mWordBase.get(mCurrentWord);
            tvTargetWord.setText(word.mTargetWord);
            etForeignWord.setText("");
            etForeignWord.setHint("");
            etForeignWord.setTextColor(getResources().getColor(R.color.answer_waiting));

            mAnswerNumber = 0;
            tvForeignTitle.setText("Перевод вариант " + Integer.toString(mAnswerNumber + 1));

            // fill answers
            mAnswers.clear();
            for (WordModel.TrainingWord training: word.mTrainingWord) {
                if(!training.mWord.equals("")) {
                    mAnswers.add(training.mWord.toLowerCase());
                }
            }
        }
        else {
            //Snackbar.make(mView, "Нет слов в базе", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
        }
    }

    private void nextWordIndex() {

        // random order
        Random r = new Random();
        int index = mCurrentWord;
        do {
            index = r.nextInt(mWordBase.size());
        }
        while(index == mCurrentWord);

        mCurrentWord = index;
/*
        // line order
        mCurrentWord++;
        */
        if(mCurrentWord >= mWordBase.size()) {
            mCurrentWord = 0;
        }
    }

    @Override
    public Boolean onButtonPressed(int button) {
        switch (button) {
            case BUTTON_DONE:{

                String checkText = etForeignWord.getText().toString();
                WordModel word = mWordBase.get(mCurrentWord);

                int index = 0;
                for (String target: mAnswers) {
                    if(checkText.toLowerCase().equals(target.toLowerCase())) {

                        etForeignWord.setTextColor(getResources().getColor(R.color.answer_waiting));
                        mAnswers.remove(index);
                        if(mAnswers.size() > 0)
                        {
                            mAnswerNumber++;
                            tvForeignTitle.setText("Перевод вариант " + Integer.toString(mAnswerNumber + 1));
                            etForeignWord.setText("");
                            etForeignWord.setHint("");
                        }
                        else {
                            nextWordIndex();

                            fillField();
                        }

                        return true;
                    }
                    index++;
                }
                etForeignWord.setTextColor(getResources().getColor(R.color.answer_wrong));

                return true;
            }

            case BUTTON_HELP: {

                etForeignWord.setText("");
                etForeignWord.setHint(mAnswers.get(0));
                return true;
            }
        }

        return false;
    }

}
