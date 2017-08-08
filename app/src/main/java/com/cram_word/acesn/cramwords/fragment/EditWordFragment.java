package com.cram_word.acesn.cramwords.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cram_word.acesn.cramwords.R;
import com.cram_word.acesn.cramwords.model.DataProvider;
import com.cram_word.acesn.cramwords.model.WordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditWordFragment extends Fragment {


    private Button btnDone;
    private Button btnCancel;

    private EditText editTargetWord;
    private EditText editForeignWord1;
    private EditText editForeignWord2;
    private EditText editForeignWord3;
    private EditText editTheme;


    public EditWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditWordFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        View view = inflater.inflate(R.layout.fragment_edit_word, container, false);

        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnDone = (Button) view.findViewById(R.id.btnDone);

        editTargetWord = (EditText) view.findViewById(R.id.editTargetWord);
        editForeignWord1 = (EditText) view.findViewById(R.id.editForeignWord1);
        editForeignWord2 = (EditText) view.findViewById(R.id.editForeignWord2);
        editForeignWord3 = (EditText) view.findViewById(R.id.editForeignWord3);

        editTheme = (EditText) view.findViewById(R.id.editTheme);

        setOnClick();

        return view;
    }

    private void setOnClick() {
        btnCancel.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = new MainFragment();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
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

}
