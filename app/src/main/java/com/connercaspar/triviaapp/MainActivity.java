package com.connercaspar.triviaapp;

import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
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

    @OnClick(R.id.delete_quiz_button)
    protected void deleteQuizClicked() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setMessage("Are you sure you would like to delete this quiz?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questionList.clear();
                Toast.makeText(MainActivity.this, "Quiz Deleted", Toast.LENGTH_SHORT).show();
            }
        }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = deleteDialog.create();
        dialog.show();
    }

    @Override
    public void saveQuestion(Question question) {
        questionList.add(question);
        Toast.makeText(this, "Question added.", Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().remove(questionCreatorFragment).commit();
    }

    @Override
    public void quizFinished() {
        getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();
        AlertDialog.Builder correctDialog = new AlertDialog.Builder(this);
        correctDialog.setMessage(R.string.correctQuestions);
        AlertDialog dialog = correctDialog.create();
        dialog.show();
    }
}
