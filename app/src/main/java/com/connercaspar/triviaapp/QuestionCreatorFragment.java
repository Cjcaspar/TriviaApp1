package com.connercaspar.triviaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import javax.security.auth.callback.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by connercaspar on 3/23/18.
 */

public class QuestionCreatorFragment extends Fragment {

    @BindView(R.id.question_edit_text)
    protected EditText questionEditText;
    @BindView(R.id.correct_edit_text)
    protected EditText correctAnswer;
    @BindView(R.id.wrong1_edit_text)
    protected EditText wrongAnswerOne;
    @BindView(R.id.wrong2_edit_text)
    protected EditText wrongAnswerTwo;
    @BindView(R.id.wrong3_edit_text)
    protected EditText wrongAnswerThree;

    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_creator, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static QuestionCreatorFragment newInstance() {

        Bundle args = new Bundle();

        QuestionCreatorFragment fragment = new QuestionCreatorFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @OnClick(R.id.save_button)
    protected void saveQuestionClicked() {

        if (questionEditText.getText().toString().length() == 0 ||
                correctAnswer.getText().toString().length() == 0 ||
                wrongAnswerOne.getText().toString().length() == 0 ||
                wrongAnswerTwo.getText().toString().length() == 0 ||
                wrongAnswerThree.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_LONG).show();
        } else {
            String questionTitle = questionEditText.getText().toString();
            String correct = correctAnswer.getText().toString();
            String wrongOne = wrongAnswerOne.getText().toString();
            String wrongTwo = wrongAnswerTwo.getText().toString();
            String wrongThree = wrongAnswerThree.getText().toString();
            Question question = new Question(questionTitle, correct, wrongOne, wrongTwo, wrongThree);
            callback.saveQuestion(question);
        }
    }

    public interface Callback {
        void saveQuestion(Question question);
    }

    public void attachView(Callback callback) {
        this.callback = callback;
    }


}
