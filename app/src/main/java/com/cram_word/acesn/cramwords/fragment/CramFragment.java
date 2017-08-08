package com.cram_word.acesn.cramwords.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.activity.MainActivity;
import com.cram_word.acesn.cramwords.model.WordModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CramFragment extends Fragment {

    private TextView tvTargetWord;
    private EditText etForeignWord;
    private Button btnCheck;
    private Button btnNext;

    private int mCurrentWord;
    private List<WordModel> mWordBase = new ArrayList<>();

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
        etForeignWord = (EditText) view.findViewById(R.id.editForeignWord1);

        btnCheck = (Button) view.findViewById(R.id.btnDone);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        Activity activity = getActivity();
        if(activity instanceof MainActivity) {
            setWordBase(((MainActivity) activity).mWordBase);
        }

        fillField();
        setOnClick();

        return view;
    }

    public void setWordBase(List<WordModel> wordBase) {
        mWordBase = wordBase;
        mCurrentWord = 0;

        fillField();
    }

    private void setOnClick() {

        btnCheck.setOnClickListener(v -> {
            String checkText = etForeignWord.getText().toString();
            WordModel word = mWordBase.get(mCurrentWord);
            for (WordModel.TrainingWord target: word.mTrainingWord) {
                if(checkText.toLowerCase().equals(target.mWord.toLowerCase())) {
                    etForeignWord.setTextColor(getResources().getColor(R.color.answer_correct));
                    return;
                }
            }

            etForeignWord.setTextColor(getResources().getColor(R.color.answer_wrong));
        });

        btnNext.setOnClickListener(v -> {
            mCurrentWord++;
            if(mCurrentWord >= mWordBase.size()) {
                mCurrentWord = 0;
            }

            fillField();
        });

    }

    private void fillField() {
        if((mWordBase != null) && (mWordBase.size() > 0)) {
            WordModel word = mWordBase.get(mCurrentWord);
            tvTargetWord.setText(word.mTargetWord);
            etForeignWord.setText("");
            etForeignWord.setTextColor(getResources().getColor(R.color.answer_waiting));
        }
        else {
            //Snackbar.make(mView, "Нет слов в базе", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
        }
    }

}
