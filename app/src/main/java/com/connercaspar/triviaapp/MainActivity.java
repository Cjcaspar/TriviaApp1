package com.connercaspar.triviaapp;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements QuestionCreatorFragment.Callback, QuizFragment.QuizCallback{

    private QuestionCreatorFragment questionCreatorFragment;
    private QuizFragment quizFragment;

    private ArrayList<Question> questionList;
    public static final String QUESTIONS_LIST = "questions_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ArrayList<Question> questionList = new ArrayList<Question>();
    }

    @OnClick(R.id.add_question_button)
    protected void addQuestionClicked (View view) {

        questionCreatorFragment = QuestionCreatorFragment.newInstance();
        questionCreatorFragment.attachView(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, questionCreatorFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(QUESTIONS_LIST , questionList);
    }

    @OnClick(R.id.take_quiz_button)
    protected void takeQuizClicked() {
        if (questionList.isEmpty()) {
            Toast.makeText(this, "You must add a question first.", Toast.LENGTH_SHORT).show();
        } else {
            quizFragment = QuizFragment.newInstance();
            quizFragment.attachView(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, quizFragment).commit();

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(QUESTIONS_LIST, (ArrayList<? extends Parcelable>) questionList);
            quizFragment.setArguments(bundle);
        }
    }

    @Override
    public void saveQuestion(Question question) {
        questionList.add(question);
        Toast.makeText(this, "Question added.", Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().remove(questionCreatorFragment).commit();
    }

}
