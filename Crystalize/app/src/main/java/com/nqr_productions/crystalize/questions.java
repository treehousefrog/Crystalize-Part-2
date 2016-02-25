package com.nqr_productions.crystalize;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class questions extends Activity {

    private boolean done;
    private int QuestionNo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        String[] questions = getResources().getStringArray(R.array.Questions);
        TextView t = (TextView) findViewById(R.id.question);
        t.setText(questions[QuestionNo]);
        findViewById(R.id.tickcross).setVisibility(View.INVISIBLE);
        findViewById(R.id.correctornot).setVisibility(View.INVISIBLE);
        findViewById(R.id.nextbutton).setVisibility(View.INVISIBLE);


    }
    public void onHintClick(View view) {

        //this is a comment - anything with two forward strokes preceding it will be ignored by Android Studio
        String[] hints = getResources().getStringArray(R.array.Hints);
        Toast toasty = Toast.makeText(getApplicationContext(), hints[QuestionNo], Toast.LENGTH_SHORT);
        toasty.show();

    }

    public void onNextClick(View view) {
        if (done) {
            String[] questions = getResources().getStringArray(R.array.Questions);
            if (QuestionNo < (questions.length - 1)) {
                QuestionNo = QuestionNo++;
                TextView t = (TextView) findViewById(R.id.question);
                t.setText(questions[QuestionNo]);


                findViewById(R.id.tickcross).setVisibility(View.INVISIBLE);
                findViewById(R.id.correctornot).setVisibility(View.INVISIBLE);
                findViewById(R.id.nextbutton).setVisibility(View.INVISIBLE);
                EditText et = (EditText) findViewById(R.id.answer);
                et.setText("");

                done = false;
            }
        }
    }

    public void answersubmitted() {
        findViewById(R.id.tickcross).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(0,0,2000,0);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.correctornot).setVisibility(View.VISIBLE);
                findViewById(R.id.nextbutton).setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.tickcross).startAnimation(animation);
    }

    public void onAnswerClick(View view) {
        if (done == false) {
            String answer = ((EditText) findViewById(R.id.answer)).getText().toString();
            String[] answers = getResources().getStringArray(R.array.Answers);
            String correctanswer = answers[QuestionNo];
            //gets the answer and correct answer from the edit text and strings.xml respectively
            correctanswer = correctanswer.toUpperCase();
            answer = answer.toUpperCase();
            //makes sure that the strings are lower case


            if (answer.equals(correctanswer)) {
                TextView t = (TextView) findViewById(R.id.correctornot);
                t.setText("CORRECT!");
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.right);
                mp.start();
                ImageView i = (ImageView) findViewById(R.id.tickcross);
                i.setImageDrawable(getDrawable(R.drawable.weirdtick));
                answersubmitted();

            } else {
                TextView t = (TextView) findViewById(R.id.correctornot);
                t.setText("CORRECT ANSWER: " + correctanswer);
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                mp.start();
                ImageView i = (ImageView) findViewById(R.id.tickcross);
                i.setImageDrawable(getDrawable(R.drawable.weirdcross));
                answersubmitted();
            }
            done = true;
        }
    }

}
