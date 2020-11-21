package com.example.moodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);

//        LoginButton loginButton = (LoginButton)findViewById(R.id.fb_login);
//        FloatingActionButton fbButton = (FloatingActionButton) findViewById(R.id.fbButton);
        // TODO: 21.11.2020 fb login - connect buttons
        //fbButton.setOnClickListener();
    }

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }

    public void showRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
        this.finish();
    }

    // TODO: 17.11.2020 Facebook logging
    public void FBLogIn() {

    }
}