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



public class leaderBoard extends AppCompatActivity{

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
        setContentView(R.layout.leaderboard);

        sharedPreferences = this.getSharedPreferences("com.example.abhineetchaudhary.quizup", Context.MODE_PRIVATE);
        userNameStr = sharedPreferences.getString("userName-xyz","None");
        Log.i("info",userNameStr);

        final appController leaderApp = new appController();
        leaderApp.getLeaderBoardData(userNameStr);

        BackBtn =(Button)findViewById(R.id.endQuizBtn);

        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long l){

            }
            @Override
            public void onFinish(){

                tableRow1 = (TableRow)findViewById(R.id.tableRow1);
                TextView tr10 = tableRow1.findViewById(R.id.tr10);
                TextView tr11 = tableRow1.findViewById(R.id.tr11);
                tr10.setText(leaderApp.categoryHighScoreArray.get(0));
                tr11.setText(leaderApp.userHighScoreArray.get(0));

                tableRow2 = (TableRow)findViewById(R.id.tableRow2);
                TextView tr20 = tableRow2.findViewById(R.id.tr20);
                TextView tr21 = tableRow2.findViewById(R.id.tr21);
                tr20.setText(leaderApp.categoryHighScoreArray.get(1));
                tr21.setText(leaderApp.userHighScoreArray.get(1));

                tableRow3 = (TableRow)findViewById(R.id.tableRow3);
                TextView tr30 = tableRow3.findViewById(R.id.tr30);
                TextView tr31 = tableRow3.findViewById(R.id.tr31);
                tr30.setText(leaderApp.categoryHighScoreArray.get(2));
                tr31.setText(leaderApp.userHighScoreArray.get(2));

                tableRow4 = (TableRow)findViewById(R.id.tableRow4);
                TextView tr40 = tableRow4.findViewById(R.id.tr40);
                TextView tr41 = tableRow4.findViewById(R.id.tr41);
                tr40.setText(leaderApp.categoryHighScoreArray.get(3));
                tr41.setText(leaderApp.userHighScoreArray.get(3));

                tableRow5 = (TableRow)findViewById(R.id.tableRow5);
                TextView tr50 = tableRow5.findViewById(R.id.tr50);
                TextView tr51 = tableRow5.findViewById(R.id.tr51);
                tr50.setText(leaderApp.categoryHighScoreArray.get(4));
                tr51.setText(leaderApp.userHighScoreArray.get(4));

            }
        }.start();

        final Intent x = new Intent(this, quizUp.class);


        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(x);
            }
        });

    }


}
