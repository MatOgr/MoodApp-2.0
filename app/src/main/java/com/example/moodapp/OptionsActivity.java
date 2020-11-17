package com.example.moodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }

    public void showRecipe(View view) {
        Intent intent = new Intent(this, Recipe.class);
        startActivity(intent);
        this.finish();
    }

    // TODO: 17.11.2020 Facebook logging
    public void FBLogIn() {}
}