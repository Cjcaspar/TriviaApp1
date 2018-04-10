package com.connercaspar.triviaapp;

import android.content.DialogInterface;
import android.os.Build;
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
        questionList = new ArrayList<>();
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
        deleteDialog.setMessage(R.string.quiz_retake).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questionList.clear();
                Toast.makeText(MainActivity.this, "Quiz Deleted", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void saveQuestion(Question question) {
        questionList.add(question);
        Toast.makeText(this, "Question added.", Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().remove(questionCreatorFragment).commit();
    }

    @Override
    public void quizFinished(int correctAnswers) {
        getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();
//        AlertDialog.Builder correctDialog = new AlertDialog.Builder(this);
//        String text = getString(R.string.correct_questions, correctAnswers);
//        correctDialog.setMessage(text);
//        AlertDialog dialog = correctDialog.create();
//        dialog.show();
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Quiz Complete")
                .setMessage(getResources().getString(R.string.correct_questions, String.valueOf(correctAnswers)))
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
