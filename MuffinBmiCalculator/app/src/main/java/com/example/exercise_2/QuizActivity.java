package com.example.exercise_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button answer1, answer2, answer3, answer4;
    private TextView score, question;
    private String mAnswer;
    private int mScore = 0;

    int questionNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionNumber = 0;
        mScore = 0;
        answer1 = (Button) findViewById(R.id.answer1Button);
        answer2 = (Button) findViewById(R.id.answer2Button);
        answer3 = (Button) findViewById(R.id.answer3Button);
        answer4 = (Button) findViewById(R.id.answer4Button);
        score = (TextView) findViewById(R.id.scoreTextView);
        question = (TextView) findViewById(R.id.questionTextView);
        score.setText(getString(R.string.score) + mScore);

        updateQuestion(questionNumber);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer1.getText() == mAnswer) {
                    mScore++;
                    score.setText(getString(R.string.score) + mScore);
                    questionNumber++;
                    updateQuestion(questionNumber);
                } else {
                    gameOver();
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer2.getText() == mAnswer) {
                    mScore++;
                    score.setText(getString(R.string.score) + mScore);
                    questionNumber++;
                    updateQuestion(questionNumber);
                } else {
                    gameOver();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer3.getText() == mAnswer) {
                    mScore++;
                    score.setText(getString(R.string.score) + mScore);
                    questionNumber++;
                    updateQuestion(questionNumber);
                } else {
                    gameOver();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer4.getText() == mAnswer) {
                    mScore++;
                    score.setText(getString(R.string.score + mScore));
                    questionNumber++;
                    updateQuestion(questionNumber);
                } else {
                    gameOver();
                }
            }
        });
    }

    private void updateQuestion(int num) {
        if (num >= 5) {
            endTheGame();
        } else {
            question.setText(Questions.getQuestion(num));
            answer1.setText(Questions.getChoice(num, 0));
            answer2.setText(Questions.getChoice(num, 1));
            answer3.setText(Questions.getChoice(num, 2));
            answer4.setText(Questions.getChoice(num, 3));
            mAnswer = Questions.getCorrectAnwer(num);
        }
    }

    private void gameOver() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage(getString(R.string.lose_quiz) + mScore)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.rerun),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton(getString(R.string.exit_quiz),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(QuizActivity.this, "", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void endTheGame() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage(getString(R.string.win_quiz))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.rerun),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton(getString(R.string.exit_quiz),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(QuizActivity.this, "", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
