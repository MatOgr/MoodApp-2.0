package com.example.moodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "https://www.thetreecenter.com/wp-content/uploads/washington-navel-orange-2.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularImageView circleImage = findViewById(R.id.circularImg);
        Picasso.get()
                .load(EXTRA_URL)
                .into(circleImage);
    }

    public void showOptions(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        //intent.putExtra(OPTION_URL, EXTRA_URL);
        startActivity(intent);
        //view.findViewById(R.id.optionsButton).setVisibility(View.INVISIBLE);
    }

}

//Icons made by <a href="https://www.flaticon.com/authors/kiranshastry" title="Kiranshastry">Kiranshastry</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>