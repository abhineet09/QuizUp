package com.example.abhineetchaudhary.quizup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String userNameStr;
    Button startQuizBtn,playAgainBtn,resetBtn,settingsBtn,setVolumeBtn,loginSubmitBtn,signUpBtn,signUpSubmitBtn,leaderBoardBtn;
    ImageButton goBackBtn,profileBtn;
    TextView qPhraseView,timerView,scoreView,currentScoreView,userHighScoreView,categoryTextView,highestScoreView,userName,userPass,userEmail;
    Button ansBtn1,ansBtn2,ansBtn3,ansBtn4;
    Button nextArrow,backArrow;
    ImageView categoryView,waitStateView;
    MediaPlayer mediaPlayer;
    ImageView animate1,animate2,animate3,animate4;
    TextView userNameView,userEmailView;
    EditText resetCurrent, resetNew;
    TableRow tableRow1,tableRow2,tableRow3,tableRow4,tableRow5;
    ConstraintLayout quizInterface;
    Boolean bool;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    Toast toast;

    public void toggleMusic(View view){
        setVolumeBtn = (Button)findViewById(R.id.setVolumeBtn);
        if(mediaPlayer.isPlaying()){
            setVolumeBtn.setText("TURN ON VOLUME");
            mediaPlayer.pause();
        }
        else{
            setVolumeBtn.setText("TURN OFF VOLUME");
            mediaPlayer.start();
        }
    }

    public void login(){
    setContentView(R.layout.login_page);
    final Intent i = new Intent(this, quizUp.class);
    final Toast fail = Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG);
    loginSubmitBtn = (Button)findViewById(R.id.loginSubmitBtn);
    loginSubmitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userName = (TextView)findViewById(R.id.userNameView);
            userPass = (TextView)findViewById(R.id.userPassView);
            Log.i("info",userName.getText().toString());
            final appController userApp = new appController();
            userApp.getUserPass(userName.getText().toString());
            new CountDownTimer(5000,1000){
                @Override
                public void onTick(long l){

                }
                @Override
                public void onFinish(){
                    if((userPass.getText().toString()).equals(userApp.userPassword)){
                        Log.i("info","Login Success");
                        userNameStr = userName.getText().toString();
                        sharedPreferences.edit().putString("userName",userNameStr).apply();
                        sharedPreferences.edit().putString("playCount",String.valueOf(0)).apply();
                        startActivity(i);
                    }
                    else{
                        Log.i("info","Login Failed");
                        fail.show();
                    }
                }
            }.start();
        }
    });

    final Toast success = Toast.makeText(this,"New User Created.",Toast.LENGTH_LONG);

    signUpBtn = (Button)findViewById(R.id.signUpBtn);
    signUpBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.signup_page);
            userEmail = (TextView)findViewById(R.id.userEmailView);
            userName = (TextView)findViewById(R.id.userNameView);
            userPass = (TextView)findViewById(R.id.userPassView);

            signUpSubmitBtn = (Button)findViewById(R.id.loginSubmitBtn);
            goBackBtn = (ImageButton)findViewById(R.id.endQuizBtn);


            signUpSubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appController userApp = new appController();
                    userApp.addNewUser(userEmail.getText().toString(), userName.getText().toString(), userPass.getText().toString());
                    success.show();
                }
            });

            goBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
        }
    });
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = this.getSharedPreferences("com.example.abhineetchaudhary.quizup", Context.MODE_PRIVATE);
        setContentView(R.layout.entry_screen);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setMax(6);

        Toast.makeText(this, "Please wait while we load. :)",Toast.LENGTH_LONG);
        new CountDownTimer(6000,1000){
            @Override
            public void onTick(long l){
                int x = 6 - (int)l/1000;
                progressBar.setProgress(x);
            }
            @Override
            public void onFinish(){
                login();
            }
        }.start();


    }
}
