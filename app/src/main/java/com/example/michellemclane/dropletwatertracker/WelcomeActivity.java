package com.example.michellemclane.dropletwatertracker;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class WelcomeActivity extends AppCompatActivity {

    private Button mLogInButton, mAnimateButton;

    private ImageView waterView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mLogInButton = (Button) findViewById(R.id.goHome_btn);

        mAnimateButton = (Button) findViewById(R.id.animate_btn);

        waterView = (ImageView) findViewById(R.id.waterView);


        mAnimateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransitionDrawable) waterView.getDrawable()).startTransition(3000);
            }
        });

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIn = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(logIn);
            }
        });
    }

}