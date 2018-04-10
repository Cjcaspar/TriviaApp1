package com.connercaspar.triviaapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by connercaspar on 3/28/18.
 */

public class QuizFragment extends Fragment {

    @BindView(R.id.question_text_view)
    protected TextView quizQuestion;
    @BindView(R.id.button6)
    protected Button quizButtonOne;
    @BindView(R.id.button7)
    protected Button quizButtonTwo;
    @BindView(R.id.button8)
    protected Button quizButtonThree;
    @BindView(R.id.button9)
    protected Button quizButtonFour;
    @BindView(R.id.question_submit_button)
    protected Button submitButton;

    private QuizCallback quizCallback;
    private List<Question> questionList;
    private Question question;
    private int questionListPosition;
    private int correctAnswers = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        questionList = new ArrayList<>();
        questionList = getArguments().getParcelableArrayList(MainActivity.QUESTIONS_LIST);
        populateQuizContent();
    }

    public static QuizFragment newInstance() {

        Bundle args = new Bundle();

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void disableButtons() {
        quizButtonOne.setClickable(false);
        quizButtonTwo.setClickable(false);
        quizButtonThree.setClickable(false);
        quizButtonFour.setClickable(false);
    }

    private void enableButtons() {
        quizButtonOne.setClickable(true);
        quizButtonTwo.setClickable(true);
        quizButtonThree.setClickable(true);
        quizButtonFour.setClickable(true);
    }

    private void populateQuizContent() {
        question = questionList.get(questionListPosition);

        quizQuestion.setText(question.getQuestion());

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(quizButtonOne);
        buttonList.add(quizButtonTwo);
        buttonList.add(quizButtonThree);
        buttonList.add(quizButtonFour);

        List<String> possibleAnswerList = new ArrayList<>();
        possibleAnswerList.add(question.getWrongOne());
        possibleAnswerList.add(question.getWrongTwo());
        possibleAnswerList.add(question.getWrongThree());
        possibleAnswerList.add(question.getCorrectAnswer());

        for (Button button : buttonList) {
            int random = (int) (Math.random() * possibleAnswerList.size() -1);
            button.setText(possibleAnswerList.get(random));
            possibleAnswerList.remove(random);
        }
    }

    @OnClick(R.id.button6)
    protected void buttonOneClicked() {
        checkAnswer(quizButtonOne.getText().toString());
    }
    @OnClick(R.id.button7)
    protected void buttonTwoClicked() {
        checkAnswer(quizButtonTwo.getText().toString());
    }
    @OnClick(R.id.button8)
    protected void buttonThreeClicked() {
        checkAnswer(quizButtonThree.getText().toString());
    }
    @OnClick(R.id.button9)
    protected void buttonFourClicked() {
        checkAnswer(quizButtonFour.getText().toString());
    }

    private void checkAnswer(String answer) {
        questionListPosition++;
        disableButtons();
        if (question.getCorrectAnswer().toString().equals(answer)) {
            quizQuestion.setText("That is correct!");
            Toast.makeText(getActivity(), "That is correct!", Toast.LENGTH_SHORT).show();
            correctAnswers++;
        } else {
            quizQuestion.setText(getString(R.string.incorrect_string, question.getCorrectAnswer()));
            Toast.makeText(getActivity(), "That is not correct", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.question_submit_button)
    protected void nextButtonClicked() {
        enableButtons();
        if (questionListPosition >= questionList.size())
        {
            quizCallback.quizFinished(correctAnswers);
        } else {
            populateQuizContent();
        }

    }


    public interface QuizCallback {
        void quizFinished(int correctAnswers);
    }

    public void attachView(QuizCallback quizCallback){
        this.quizCallback = quizCallback;
    }

}
