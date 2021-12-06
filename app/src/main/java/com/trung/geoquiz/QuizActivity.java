package com.trung.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
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
    private Boolean mIsButtonEnable = true;
    private TextView mQuestionTextView;
    private int mNumberOfCorrectedAnswer = 0;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String BUTTON_ENABLE = "enable";

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
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(BUTTON_ENABLE, mIsButtonEnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d(TAG, "onCreate(Bundle) called");

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mQuestionTextView.setOnClickListener(view -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        if (savedInstanceState != null) {
            mIsButtonEnable = savedInstanceState.getBoolean(BUTTON_ENABLE, true);
            setButtonStatus(mIsButtonEnable, mTrueButton, mFalseButton);
        }

        mTrueButton.setOnClickListener(view -> {
            checkAnswer(true);
            mIsButtonEnable = false;
            setButtonStatus(mIsButtonEnable, mTrueButton, mFalseButton);
            if (mCurrentIndex == (mQuestionBank.length - 1)) {
                getScore();
            }
        });


        mFalseButton.setOnClickListener(view -> {
            checkAnswer(false);
            mIsButtonEnable = false;
            setButtonStatus(mIsButtonEnable, mTrueButton, mFalseButton);
            if (mCurrentIndex == (mQuestionBank.length - 1)) {
                getScore();
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(view -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
            setButtonStatus(true, mTrueButton, mFalseButton);
        });

        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(view -> {
            mCurrentIndex = ((mCurrentIndex - 1) % mQuestionBank.length + mQuestionBank.length) % mQuestionBank.length;
            updateQuestion();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
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
            mNumberOfCorrectedAnswer++;
        } else {
            messageResID = R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this, messageResID, Toast.LENGTH_SHORT).show();
    }

    private void setButtonStatus(boolean buttonStatus, @NonNull Button... bs) {
        for (Button b : bs) {
            b.setEnabled(buttonStatus);
        }
    }

    private void getScore() {
        double score = ((double) mNumberOfCorrectedAnswer) / ((double) mQuestionBank.length) * 100;
        Toast.makeText(QuizActivity.this, String.format("Score: %2.2f%%", score), Toast.LENGTH_LONG).show();
    }
}