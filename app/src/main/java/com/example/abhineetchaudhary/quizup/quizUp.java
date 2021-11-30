package com.example.abhineetchaudhary.quizup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class quizUp extends AppCompatActivity{

    String nextCategory;
    String userNameStr;
    Button startQuizBtn,playAgainBtn,goToMainMenu,settingsBtn,setVolumeBtn,loginSubmitBtn,signUpBtn,resetUserHighScores,leaderBoardBtn;
    ImageButton goBackBtn,profileBtn;
    TextView qPhraseView,timerView,scoreView,currentScoreView,userHighScoreView,categoryTextView,highestScoreView,waitStateTextView;
    Button ansBtn1,ansBtn2,ansBtn3,ansBtn4;
    ArrayList<String> questionGlobalObject = new ArrayList<String>();
    CountDownTimer quizCountdown;
    Integer score=0,categoryIndex=1;
    String userHighScore,categoryHighScore;
    Button nextArrow,backArrow;
    ImageView categoryView,waitStateView;
    MediaPlayer mediaPlayer;
    ImageView animate1,animate2,animate3,animate4;
    TextView newHighScoreText;
    TextView userNameView,userEmailView;
    EditText resetCurrent, resetNew;
    Button resetBtn,BackBtn,EndQuizBtn;
    TableRow tableRow1,tableRow2,tableRow3,tableRow4,tableRow5;
    ConstraintLayout quizInterface;
    Boolean bool;
    CountDownTimer quizPlay;
    Integer playCount;

    SharedPreferences sharedPreferences;

    public void toggleMusic(View view){
        setVolumeBtn = (Button)findViewById(R.id.setVolumeBtn);
        if(mediaPlayer.isPlaying()){
            setVolumeBtn.setText("TURN ON VOLUME");
            playCount=1;
            sharedPreferences.edit().putString("playCount",String.valueOf(playCount)).apply();
            mediaPlayer.stop();
        }
        else{
            setVolumeBtn.setText("TURN OFF VOLUME drr");
            mediaPlayer = MediaPlayer.create(this,R.raw.music1);
            playCount=0;
            sharedPreferences.edit().putString("playCount",String.valueOf(playCount)).apply();
            mediaPlayer.start();
            mediaPlayer.setVolume(55,55);
        }
    }

    public void startQuiz(int categoryIndex){

        Log.i("info","at quiz start 123");

        qPhraseView = (TextView)findViewById(R.id.qPhraseView);
        ansBtn1 = (Button)findViewById(R.id.ansBtn1);
        ansBtn2 = (Button)findViewById(R.id.ansBtn2);
        ansBtn3 = (Button)findViewById(R.id.ansBtn3);
        ansBtn4 = (Button)findViewById(R.id.ansBtn4);
        scoreView = (TextView)findViewById(R.id.scoreView);

        final appController quizApp = new appController();
        quizApp.getQuestion(categoryIndex);
        quizApp.getHighScore(categoryIndex,userNameStr);
        quizPlay = new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                quizInterface = (ConstraintLayout)findViewById(R.id.waitStateView);
                quizInterface.setVisibility(View.VISIBLE);
                waitStateView = (ImageView)findViewById(R.id.waitView);
                waitStateView.setVisibility(View.VISIBLE);
                waitStateTextView = (TextView)findViewById(R.id.waitTextView);
                waitStateTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                quizInterface = (ConstraintLayout)findViewById(R.id.waitStateView);
                quizInterface.setVisibility(View.INVISIBLE);
                waitStateView = (ImageView)findViewById(R.id.waitView);
                waitStateView.setVisibility(View.INVISIBLE);
                waitStateTextView = (TextView)findViewById(R.id.waitTextView);
                waitStateTextView.setVisibility(View.INVISIBLE);
                scoreView.setText(score.toString());
                resetAnsButtonBackground();
                qPhraseView.setText(quizApp.questionObject.get(0).toString());
                ansBtn1.setText(quizApp.questionObject.get(1).toString());
                ansBtn2.setText(quizApp.questionObject.get(2).toString());
                ansBtn3.setText(quizApp.questionObject.get(3).toString());
                ansBtn4.setText(quizApp.questionObject.get(4).toString());
                questionGlobalObject = quizApp.questionObject;
                userHighScore = quizApp.userHighScore;
                categoryHighScore = quizApp.categoryHighScore;
            }
        };
        quizPlay.start();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Intent qz = new Intent(this, quizUp.class);
        EndQuizBtn = (Button)findViewById(R.id.endQuizBtn);
        EndQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Click on Yes to Quit")
                        .setTitle("Are you sure?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        quizPlay.cancel();
                        if(quizPlay != null) {
                            quizPlay.cancel();
                            quizPlay = null;
                        }
                        quizCountdown.cancel();
                        if(quizCountdown != null) {
                            quizCountdown.cancel();
                            quizCountdown = null;
                        }
                        startActivity(qz);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Log.i("info","User Cancelled");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void checkAns(View view){
        Log.i("info",String.valueOf(view.getTag()));
        Integer answeredOption = Integer.parseInt(view.getTag().toString());
        if(answeredOption == Integer.parseInt(questionGlobalObject.get(5))){
            Log.i("info","Correct Answer!!");
            view.setBackgroundColor(Color.GREEN);
            score += 10;

        }
        else{
            Log.i("info","Incorrect Answer!!");
            view.setBackgroundColor(Color.RED);
        }
        startQuiz(categoryIndex);
    }

    public void resetAnsButtonBackground(){
        ansBtn1 = (Button)findViewById(R.id.ansBtn1);
        ansBtn2 = (Button)findViewById(R.id.ansBtn2);
        ansBtn3 = (Button)findViewById(R.id.ansBtn3);
        ansBtn4 = (Button)findViewById(R.id.ansBtn4);

        ansBtn1.setBackgroundColor(ansBtn1.getContext().getResources().getColor(R.color.teal_shade2));
        ansBtn2.setBackgroundColor(ansBtn2.getContext().getResources().getColor(R.color.teal_shade2));
        ansBtn3.setBackgroundColor(ansBtn3.getContext().getResources().getColor(R.color.teal_shade2));
        ansBtn4.setBackgroundColor(ansBtn4.getContext().getResources().getColor(R.color.teal_shade2));

    }

    public void endQuiz(){

        Toast.makeText(this,"Quiz has been completed!!",Toast.LENGTH_LONG).show();
        Log.i("info","here @ end quiz");
        setContentView(R.layout.game_over);
        appController quizEnd = new appController();
        Log.i("info","here");
        if(Integer.parseInt(userHighScore) < score){
            quizEnd.saveHighScore(categoryIndex,score,userNameStr);

            animate1 = (ImageView)findViewById(R.id.animate1);
            animate2 = (ImageView)findViewById(R.id.animate2);
            animate3 = (ImageView)findViewById(R.id.animate3);
            animate4 = (ImageView)findViewById(R.id.animate4);
            newHighScoreText = (TextView)findViewById(R.id.newHighScoreView);

            animate1.setVisibility(View.VISIBLE);
            animate2.setVisibility(View.VISIBLE);
            animate3.setVisibility(View.VISIBLE);
            animate4.setVisibility(View.VISIBLE);
            newHighScoreText.setVisibility(View.VISIBLE);
        }
        if(Integer.parseInt(categoryHighScore) < score){
            quizEnd.saveHighScore(categoryIndex,score,"Highest");

            animate1 = (ImageView)findViewById(R.id.animate1);
            animate2 = (ImageView)findViewById(R.id.animate2);
            animate3 = (ImageView)findViewById(R.id.animate3);
            animate4 = (ImageView)findViewById(R.id.animate4);
            newHighScoreText = (TextView)findViewById(R.id.newHighScoreView);

            animate1.setVisibility(View.VISIBLE);
            animate2.setVisibility(View.VISIBLE);
            animate3.setVisibility(View.VISIBLE);
            animate4.setVisibility(View.VISIBLE);
            newHighScoreText.setVisibility(View.VISIBLE);
        }

        currentScoreView = (TextView)findViewById(R.id.currentScoreView);
        currentScoreView.setText(score.toString());
        userHighScoreView = (TextView)findViewById(R.id.userHighScoreView);
        userHighScoreView.setText(userHighScore.toString());
        highestScoreView = (TextView)findViewById(R.id.highestScoreView);
        highestScoreView.setText(categoryHighScore);

        Log.i("info",userHighScore);

        playAgainBtn = (Button)findViewById(R.id.playAgainBtn);
        goToMainMenu = (Button)findViewById(R.id.goToMainMenuBtn);

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.quiz_interface);
                quizCountdown.start();
                score = 0;
                startQuiz(categoryIndex);
            }
        });

        final Intent i = new Intent(this, quizUp.class);
        goToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        sharedPreferences = this.getSharedPreferences("com.example.abhineetchaudhary.quizup", Context.MODE_PRIVATE);
        userNameStr = sharedPreferences.getString("userName","None");
        Log.i("info",userNameStr);
        playCount = Integer.parseInt(sharedPreferences.getString("playCount","None"));

        Toast.makeText(this,"Welcome!!",Toast.LENGTH_LONG).show();

        profileBtn = (ImageButton)findViewById(R.id.profileBtn);

        if(playCount == 0){
            mediaPlayer = MediaPlayer.create(this,R.raw.music1);
            mediaPlayer.start();
            mediaPlayer.setVolume(55,55);
        }


        startQuizBtn = (Button)findViewById(R.id.startQuizBtn);
        qPhraseView = (TextView)findViewById(R.id.qPhraseView);
        ansBtn1 = (Button)findViewById(R.id.ansBtn1);
        ansBtn2 = (Button)findViewById(R.id.ansBtn2);
        ansBtn3 = (Button)findViewById(R.id.ansBtn3);
        ansBtn4 = (Button)findViewById(R.id.ansBtn4);
        settingsBtn = (Button)findViewById(R.id.settingsBtn);

        nextArrow = (Button)findViewById(R.id.nextArrow);
        backArrow = (Button)findViewById(R.id.backArrow);
        categoryView = (ImageView)findViewById(R.id.categoryView);
        categoryTextView = (TextView)findViewById(R.id.categoryTextView);

        leaderBoardBtn = (Button)findViewById(R.id.leaderBoardBtn);
        final Intent z = new Intent(this, leaderBoard.class);

        leaderBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(z);
            }
        });

        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.quiz_interface);
                //categoryIndex=1;
                startQuiz(categoryIndex);
                quizCountdown.start();
            }
        });

        quizCountdown = new CountDownTimer(90000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("info", String.valueOf(millisUntilFinished/1000));
                timerView = (TextView)findViewById(R.id.timerView);
                timerView.setText(String.valueOf(millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                endQuiz();
            }
        };

        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info",String.valueOf(categoryView.getTag()));
                String currentCategory = String.valueOf(categoryView.getTag());

                if(currentCategory.equals("technology")){
                    nextCategory = "computer fundamentals";
                    categoryView.setImageResource(R.drawable.computerfundamentals);
                    categoryTextView.setText("Computer Fundamentals");
                    categoryIndex = 2;
                }
                else if(currentCategory.equals("computer fundamentals")){
                    nextCategory = "verbal";
                    categoryView.setImageResource(R.drawable.verbal);
                    categoryTextView.setText("Verbal");
                    categoryIndex=3;
                }
                else if(currentCategory.equals("verbal")){
                    nextCategory = "logical";
                    categoryView.setImageResource(R.drawable.logical);
                    categoryTextView.setText("Logical");
                    categoryIndex=4;
                }
                else if(currentCategory.equals("logical")){
                    nextCategory = "qualitative";
                    categoryView.setImageResource(R.drawable.qualitative);
                    categoryTextView.setText("Qualitative");
                    categoryIndex=5;
                }
                else if(currentCategory.equals("qualitative")){
                    nextCategory = "technology";
                    categoryView.setImageResource(R.drawable.techcategory);
                    categoryTextView.setText("Technology");
                    categoryIndex=1;
                }
                Log.i("info",nextCategory);
                categoryView.setTag(nextCategory);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info",String.valueOf(categoryView.getTag()));
                String currentCategory = String.valueOf(categoryView.getTag());

                if(currentCategory.equals("technology")){
                    nextCategory = "qualitative";
                    categoryView.setImageResource(R.drawable.qualitative);
                    categoryTextView.setText("Qualitative");
                    categoryIndex=5;
                }
                else if(currentCategory.equals("computer fundamentals")){
                    nextCategory = "technology";
                    categoryView.setImageResource(R.drawable.techcategory);
                    categoryTextView.setText("Technology");
                    categoryIndex=1;
                }
                else if(currentCategory.equals("verbal")){
                    nextCategory = "computer fundamentals";
                    categoryView.setImageResource(R.drawable.computerfundamentals);
                    categoryTextView.setText("Computer Fundamentals");
                    categoryIndex=2;
                }
                else if(currentCategory.equals("logical")){
                    nextCategory = "verbal";
                    categoryView.setImageResource(R.drawable.verbal);
                    categoryTextView.setText("Verbal");
                    categoryIndex=3;
                }
                else if(currentCategory.equals("qualitative")){
                    nextCategory = "logical";
                    categoryView.setImageResource(R.drawable.logical);
                    categoryTextView.setText("Logical");
                    categoryIndex=4;
                }
                //Log.i("info",nextCategory);
                categoryView.setTag(nextCategory);
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Intent ax = new Intent(this, quizUp.class);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.settings_page);
                goBackBtn = (ImageButton)findViewById(R.id.goBackBtn);
                goBackBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(ax);
                    }
                });
                BackBtn = (Button)findViewById(R.id.endQuizBtn);
                BackBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(ax);
                    }
                });

                resetUserHighScores = (Button)findViewById(R.id.resetUserScores);

                resetUserHighScores.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("info","resetting user high scores");
                        builder.setMessage("Click on OK to continue.")
                                .setTitle("Are You Sure?");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                appController resetApp = new appController();
                                resetApp.resetUserHighScore(userNameStr);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                Log.i("info","User Cancelled");
                            }
                        });

                        AlertDialog dialog = builder.create();

                        dialog.show();
                    }
                });

            }
        });

        final Intent i = new Intent(this, userProfile.class);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

    }


}
