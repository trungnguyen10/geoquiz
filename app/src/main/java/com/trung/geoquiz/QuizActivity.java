package com.trung.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mQuestionTextView.setOnClickListener(view -> {
            mCurrentIndex= (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(view -> {
            checkAnswer(true);
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(view -> {
            checkAnswer(false);
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(view -> {
            mCurrentIndex= (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });

        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(view -> {
            mCurrentIndex = ((mCurrentIndex - 1) % mQuestionBank.length + mQuestionBank.length) % mQuestionBank.length;
            updateQuestion();
        });
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResID();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResID = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResID = R.string.correct_toast;
        } else {
            messageResID = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this, messageResID, Toast.LENGTH_SHORT).show();
    }
}