package com.example.moodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ImageView img1 = findViewById(R.id.imageButton);
        Picasso.get()
                .load("https://www.thetreecenter.com/wp-content/uploads/washington-navel-orange-2.jpg")
                .into(img1);
        ImageView img2 = findViewById(R.id.imageButton2);
        Picasso.get()
                .load("https://www.thetreecenter.com/wp-content/uploads/washington-navel-orange-2.jpg")
                .into(img2);
    }

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }
}