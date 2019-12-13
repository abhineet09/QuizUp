package com.example.abhineetchaudhary.quizup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class userProfile extends AppCompatActivity{

    String userNameStr;

    Button startQuizBtn,playAgainBtn,settingsBtn,setVolumeBtn,loginSubmitBtn,signUpBtn,leaderBoardBtn;
    ImageButton goBackBtn,profileBtn;
    TextView qPhraseView,timerView,scoreView,currentScoreView,userHighScoreView,categoryTextView,highestScoreView;
    Button ansBtn1,ansBtn2,ansBtn3,ansBtn4;
    Button nextArrow,backArrow;
    ImageView categoryView,waitStateView;
    ImageView animate1,animate2,animate3,animate4;
    TextView userNameView,userEmailView;
    EditText resetCurrent, resetNew;
    Button resetBtn,BackBtn;
    TableRow tableRow1,tableRow2,tableRow3,tableRow4,tableRow5;
    ConstraintLayout quizInterface;
    Boolean bool;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        sharedPreferences = this.getSharedPreferences("com.example.abhineetchaudhary.quizup", Context.MODE_PRIVATE);
        userNameStr = sharedPreferences.getString("userName","None");
        //Log.i("info",userNameStr);

        final appController appUser = new appController();
        appUser.getUserData(userNameStr);
        userNameView = (TextView)findViewById(R.id.userNameView);
        userEmailView = (TextView)findViewById(R.id.userEmailView);

        resetBtn = (Button)findViewById(R.id.resetBtn);
        BackBtn = (Button)findViewById(R.id.endQuizBtn);

        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l){

            }
            @Override
            public void onFinish(){
                userNameView.setText(userNameStr);
                userEmailView.setText(appUser.userEmail);
            }
        }.start();

        final Toast success = Toast.makeText(this,"Password Reset",Toast.LENGTH_LONG);
        final Toast fail = Toast.makeText(this,"Passwords Do not Match",Toast.LENGTH_LONG);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCurrent = (EditText)findViewById(R.id.resetCurrent);
                resetNew = (EditText)findViewById(R.id.resetNew);

                Log.i("info",String.valueOf(resetCurrent.getText()));
                Log.i("info",String.valueOf(appUser.userPass));

                if(String.valueOf(resetCurrent.getText()).equals(appUser.userPass)){
                    appUser.resetUserPassword(userNameStr,String.valueOf(resetNew.getText()));
                    success.show();
                }
                else{
                    fail.show();
                }
            }
        });

        final Intent qz = new Intent(this, quizUp.class);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(qz);
            }
        });


    }


}
