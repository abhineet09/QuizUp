package com.example.abhineetchaudhary.quizup;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class appController {

    DatabaseReference mDatabaseRef,mScoresRef,mUserRef,mCatRef;
    String qId_to_fetch;
    ArrayList<String> questionObject = new ArrayList<String>();
    String userHighScore,categoryHighScore,userPassword;
    String userPass, userEmail;
    ArrayList<String> userHighScoreArray = new ArrayList<String>();
    ArrayList<String> categoryHighScoreArray = new ArrayList<String>();

    appController(){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Questions");
    }

    public int questionIndex(int categoryIndex){
        Random qId = new Random();
        if(categoryIndex == 1){
            return (qId.nextInt(120-101)+101);
        }
        else if(categoryIndex == 2){
            return (qId.nextInt(210-201)+201);
        }
        else if(categoryIndex == 3){
            return (qId.nextInt(305-301)+301);
        }
        else if(categoryIndex == 4){
            return (qId.nextInt(405-401)+401);
        }
        else if(categoryIndex == 5){
            return (qId.nextInt(505-501)+501);
        }
        return 0;
    }

    public boolean getQuestion(int categoryIndex){
        qId_to_fetch = String.valueOf(questionIndex(categoryIndex));
        Log.i("info","Question selected = " + qId_to_fetch);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String qPhrase = dataSnapshot.child(String.valueOf(qId_to_fetch)).child("questionPhrase").getValue(String.class);
                String qOption1 = dataSnapshot.child(String.valueOf(qId_to_fetch)).child("questionOptions").child("1").getValue(String.class);
                String qOption2 = dataSnapshot.child(String.valueOf(qId_to_fetch)).child("questionOptions").child("2").getValue(String.class);
                String qOption3 = dataSnapshot.child(String.valueOf(qId_to_fetch)).child("questionOptions").child("3").getValue(String.class);
                String qOption4 = dataSnapshot.child(String.valueOf(qId_to_fetch)).child("questionOptions").child("4").getValue(String.class);
                String correctIndex = String.valueOf(dataSnapshot.child(String.valueOf(qId_to_fetch)).child("correctOptionIndex").getValue(Integer.class));
                questionObject.add(qPhrase);
                questionObject.add(qOption1);questionObject.add(qOption2);questionObject.add(qOption3);questionObject.add(qOption4);
                questionObject.add(correctIndex);
                Log.i("info",questionObject.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }

    public void getHighScore(int categoryIndex, final String userId){
        mScoresRef = FirebaseDatabase.getInstance().getReference().child("Scores").child(String.valueOf(userId)).child(String.valueOf(categoryIndex));
        mScoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userHighScore = String.valueOf(dataSnapshot.getValue(Integer.class));
                Log.i("info","user high score = "+userHighScore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mScoresRef = FirebaseDatabase.getInstance().getReference().child("Scores").child(String.valueOf("Highest")).child(String.valueOf(categoryIndex));
        mScoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryHighScore = String.valueOf(dataSnapshot.getValue(Integer.class));
                Log.i("info","user high score = "+categoryHighScore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveHighScore(int categoryIndex, int newHighScore, String userId){
        mScoresRef = FirebaseDatabase.getInstance().getReference().child("Scores").child(String.valueOf(userId)).child(String.valueOf(categoryIndex));
        mScoresRef.setValue(newHighScore);
    }

    public void getUserPass(String userName){
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("password");
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("info",String.valueOf(dataSnapshot.getValue(String.class)));
                userPassword = String.valueOf(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addNewUser(String userEmail, String userName, String userPass){
        Log.i("db","here");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("email",userEmail);
        dataMap.put("password",userPass);
        mUserRef.setValue(dataMap);

        mScoresRef = FirebaseDatabase.getInstance().getReference().child("Scores");
        HashMap<String, Integer> dataMap1 = new HashMap<String, Integer>();
        dataMap1.put("1",0);
        dataMap1.put("2",0);
        dataMap1.put("3",0);
        dataMap1.put("4",0);
        dataMap1.put("5",0);
        mScoresRef.child(userName).setValue(dataMap1);
    }

    public void getUserData(final String userName){
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userPass = String.valueOf(dataSnapshot.child("password").getValue());
                userEmail = String.valueOf(dataSnapshot.child("email").getValue());
                Log.i("info",userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void resetUserPassword(String userName,String userNewPass){
        Log.i("username",userName);
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("password");
        mUserRef.setValue(userNewPass);
    }

    public void getLeaderBoardData(String userName){
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Scores").child(userName);
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                    Log.i("info",childDataSnapshot.getValue(Integer.class).toString());
                    userHighScoreArray.add(childDataSnapshot.getValue(Integer.class).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mCatRef = FirebaseDatabase.getInstance().getReference().child("Scores").child("Highest");
        mCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                    Log.i("info",childDataSnapshot.getValue(Integer.class).toString());
                    categoryHighScoreArray.add(childDataSnapshot.getValue(Integer.class).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void resetUserHighScore(String userName){
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Scores");
        HashMap<String, Integer> dataMap = new HashMap<String, Integer>();
        dataMap.put("1",0);
        dataMap.put("2",0);
        dataMap.put("3",0);
        dataMap.put("4",0);
        dataMap.put("5",0);
        mUserRef.child(userName).setValue(dataMap);
    }

}
