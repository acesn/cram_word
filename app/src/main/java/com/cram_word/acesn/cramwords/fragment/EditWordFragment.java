package com.cram_word.acesn.cramwords.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.activity.ActivityAction;
import com.cram_word.acesn.cramwords.activity.MainActivity;
import com.cram_word.acesn.cramwords.model.DataProvider;
import com.cram_word.acesn.cramwords.model.WordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditWordFragment extends Fragment implements MainActivity.FragmentInteraction {


    private Button btnDone;
    private Button btnCancel;

    private EditText editTargetWord;
    private EditText editForeignWord1;
    private EditText editForeignWord2;
    private EditText editForeignWord3;
    private EditText editTheme;
    private View mView;


    public EditWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EditWordFragment.
     */
    public static EditWordFragment newInstance(String param1, String param2) {
        EditWordFragment fragment = new EditWordFragment();
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
        mView = inflater.inflate(R.layout.fragment_edit_word, container, false);

        btnCancel = (Button) mView.findViewById(R.id.btnCancel);
        btnDone = (Button) mView.findViewById(R.id.btnDone);

        editTargetWord = (EditText) mView.findViewById(R.id.editTargetWord);
        editForeignWord1 = (EditText) mView.findViewById(R.id.editForeignWord1);
        editForeignWord2 = (EditText) mView.findViewById(R.id.editForeignWord2);
        editForeignWord3 = (EditText) mView.findViewById(R.id.editForeignWord3);

        editTheme = (EditText) mView.findViewById(R.id.editTheme);

        setOnClick();

        Activity activity = getActivity();
        // config toolbar
        if(activity instanceof ActivityAction) {
            ((ActivityAction)activity).setToolbarButton(ActivityAction.BUTTON_DONE);
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return mView;
    }

    private void setOnClick() {
        btnCancel.setOnClickListener(v -> {

            Activity activity = getActivity();
            if(activity instanceof ActivityAction) {
                ((ActivityAction) activity).setFragment(ActivityAction.FRAGMENT_MAIN);
            }
        });

        btnDone.setOnClickListener(v -> {

            if((editTargetWord.getText().length() < 1) ||
                    (editForeignWord1.getText().length() < 1))
            {
                Snackbar.make(v, "Поля \"Изучаемое слово\" и \"Перевод 1\" должны быть заполнены", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                return;
            }

            String[] foreign = new String[3];
            foreign[0] = editForeignWord1.getText().toString();
            foreign[1] = editForeignWord2.getText().toString();
            foreign[2] = editForeignWord3.getText().toString();

            WordModel newWord = new WordModel(editTargetWord.getText().toString(),
                                    foreign,
                                    editTheme.getText().toString());

            DataProvider.getInstance().addWord(newWord);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = new MainFragment();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

    }

    @Override
    public Boolean onButtonPressed(int button) {
        if (button == BUTTON_DONE) {
            if((editTargetWord.getText().length() < 1) ||
                    (editForeignWord1.getText().length() < 1))
            {
                Snackbar.make(mView, "Поля \"Изучаемое слово\" и \"Перевод 1\" должны быть заполнены", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                return true;
            }

            String[] foreign = new String[3];
            foreign[0] = editForeignWord1.getText().toString();
            foreign[1] = editForeignWord2.getText().toString();
            foreign[2] = editForeignWord3.getText().toString();

            WordModel newWord = new WordModel(editTargetWord.getText().toString(),
                    foreign,
                    editTheme.getText().toString());

            DataProvider.getInstance().addWord(newWord);

            Snackbar.make(mView, "Слово добавлено", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            editTargetWord.setText("");
            editForeignWord1.setText("");
            editForeignWord2.setText("");
            editForeignWord3.setText("");
        }
        return true;
    }


}
