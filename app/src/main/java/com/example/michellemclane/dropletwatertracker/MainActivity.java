package com.example.michellemclane.dropletwatertracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //This is the private instance of my app's buttons
    private Button mWater8Button, mWater16Button, mWater24Button, mUndoButton, mDailyButton;
    //This is to update the OnClick Switch case
    private int current = 0;
    private int totalWater = 72;
    private int rem = 72;
    private int add;
    //This is to update the currentWater
    private TextView mCurrentWater;
    //This is the TextView of the Remaining water amount
    private TextView mRemainingWater;
    //This is the reference to my Firebasedatabase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshScreen();

        mRemainingWater = (TextView) findViewById(R.id.remaining_number);

        mCurrentWater = (TextView) findViewById(R.id.current_number);

        mWater8Button = (Button) findViewById(R.id.water8_btn);
        mWater16Button = (Button) findViewById(R.id.water16_btn);
        mWater24Button = (Button) findViewById(R.id.water24_btn);
        mUndoButton = (Button) findViewById(R.id.undo_btn);
        mDailyButton = (Button) findViewById(R.id.goal_label);

        mWater8Button.setOnClickListener(this);
        mWater16Button.setOnClickListener(this);
        mWater24Button.setOnClickListener(this);
        mUndoButton.setOnClickListener(this);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Set Higher Goal?");
                builder.setMessage("Would you like to reset your Daily Goal to a higher ounce?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goal = new Intent(MainActivity.this, GoalActivity.class);
                        startActivity(goal);
                    }
                });
                builder.show();
            }
        });

    }
        public void sendDialog (Context context) {
            new AlertDialog.Builder(context)
                    .setTitle("Set Goal")
                    .setMessage("Would you liket to select a higher goal?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goal = new Intent(MainActivity.this, GoalActivity.class);
                            startActivity(goal);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.water8_btn:
                add = 8;
                current = current + add;
                rem = rem - add;
                break;
            case R.id.water16_btn:
                add = 16;
                current = current + add;
                rem = rem - add;
                break;
            case R.id.water24_btn:
                add = 24;
                current = current + add;
                rem = rem - add;
                break;
            case R.id.undo_btn:
                current = current - add;
                rem = rem + add;
                break;
        }
        mCurrentWater.setText(Integer.toString(current));
        mRemainingWater.setText(Integer.toString(rem));
        //To make the app's data update in realtime
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Create a HashMap to store multiple values in one everytime the button click
        HashMap<String, Integer> dataMap = new HashMap<>();
        dataMap.put("Total", totalWater);
        dataMap.put("Current", current);
        dataMap.put("Remaining Water", rem);

        //Goes to root, makes a new child and adds a new item into the Hashmap
        mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    //Toast.makeText(MainActivity.this, "Stored..", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void refreshScreen() {
        TextView goalDisplay = (TextView) findViewById(R.id.daily_number);
        int goalSelected = GoalActivity.getGoalSelected(this);
        goalDisplay.setText("" + goalSelected);
    }

}
