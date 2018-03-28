package com.connercaspar.triviaapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by connercaspar on 3/28/18.
 */

public class Question implements Parcelable {


    private String question;
    private String correctAnswer;
    private String wrongOne;
    private String wrongTwo;
    private String wrongThree;

    public Question(String question, String correctAnswer, String wrongOne, String wrongTwo, String wrongThree) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongOne = wrongOne;
        this.wrongTwo = wrongTwo;
        this.wrongThree = wrongThree;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongOne() {
        return wrongOne;
    }

    public String getWrongTwo() {
        return wrongTwo;
    }

    public String getWrongThree() {
        return wrongThree;
    }

    protected Question(Parcel in) {
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
