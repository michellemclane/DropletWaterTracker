package com.example.michellemclane.dropletwatertracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MichelleMcLane on 4/11/17.
 */

public class GoalActivity extends AppCompatActivity {

    public static Intent makeIntent (Context context) {
        return new Intent(context, GoalActivity.class);
    }

    private ImageButton mGoSettingsButton;

    private TextView mCustomGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        createRadioGoalButton();

        int savedValue = getGoalSelected(this);
        Toast.makeText(this, "Saved value: " + savedValue, Toast.LENGTH_SHORT).show();

        //saveGoalButton();

        mGoSettingsButton = (ImageButton) findViewById(R.id.goSettings_btn);

        mGoSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goalSettings = new Intent(GoalActivity.this, MainActivity.class);
                startActivity(goalSettings);
            }
        });
    }

    //This class creates all the radio buttons for Goal options
    private void createRadioGoalButton() {
        RadioGroup goalGroup = (RadioGroup) findViewById(R.id.radioGoalGroup);

        int[] goalArrays = getResources().getIntArray(R.array.num_goal_options);

        for (int i = 0; i < goalArrays.length; i++) {
           final int goalArray = goalArrays[i];

            RadioButton goalButton = new RadioButton(this);
            goalButton.setText(getString(R.string.daily_goal) + goalArray);

            goalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(GoalActivity.this, "You selected " + goalArray,
                            Toast.LENGTH_SHORT).show();

                    saveNumGoalSelected(goalArray);
                }
            });

            goalGroup.addView(goalButton);

            //Select Default RadioButton
            if (goalArray == getGoalSelected(this)) {
                goalButton.setChecked(true);
            }
        }
    }

    private void saveNumGoalSelected(int goalArray) {
        SharedPreferences prefs = this.getSharedPreferences("GoalPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Goal Selected ", goalArray);
        editor.apply();
    }

    static public int getGoalSelected (Context context)  {
      SharedPreferences prefs = context.getSharedPreferences("GoalPrefs", MODE_PRIVATE);
        return prefs.getInt("Goal Selected ", 74);
    }

}
