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
        //TextView txt1 = findViewById(R.id.dishNameText);
        //txt1.setText("Pizza jest potrawą kuchni włoskiej, obecnie szeroko rozpowszechnioną na całym świecie i zaliczaną do dań typu fast food. W wersji podstawowej jest to płaski placek z wytrawnego ciasta makaronowego; znacznie później zaczęto także używać ciasta drożdżowego (focaccia). Pizza pieczona jest w bardzo mocno nagrzanym piecu.");
    }

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }
}

//{ "title": "Pizza", "description": "Pizza jest potrawą kuchni włoskiej, obecnie szeroko rozpowszechnioną na całym świecie i zaliczaną do dań typu fast food. W wersji podstawowej jest to płaski placek z wytrawnego ciasta makaronowego; znacznie później zaczęto także używać ciasta drożdżowego (focaccia). Pizza pieczona jest w bardzo mocno nagrzanym piecu.", "ingredients": [ "3 szklanki mąki pszennej", "1 łyżeczka soli", "przyprawy do smaku (oregano, bazylia i słodka papryka)", "1 szklanka ciepłej wody", "50g świeżych drożdży", "3 łyżeczki oliwy z oliwek lub oleju", "szczypta cukru ", "sos pomidorowy" ], "preparing": [ "Suche składniki dokładnie mieszamy.", "Drożdże zalawamy ciepłą wodą, olejem i cukrem. Odstawiamy do wyrośnięcia.", "Gotowy płyn wlewamy do mąki i mieszam najpierw łyżką, potem zagniatamy ręką.", "Ciasto odstawiamy pod przykryciem do wyrośnięcia na ok. 30 minut. ", "Rozgrzać piekarnik do 250 st. C.", "Na papierze do pieczenia uformować z gotowego ciasta placki. Wychodzą dwie cienkie pizze o średnicy 30cm. Jednak ciasto to również nadaje się na wykonanie pizzy na grubym cieście.", "Posmarować spody sosem pomidorowym (przepis na sos) . Można już w tym momencie nałożyć na wierzch pizzy swoje ulubione składniki.", "Piec ok. 7-10 minut." ], "imgs": [ "http://mooduplabs.com/test/pizza1.jpg", "http://mooduplabs.com/test/pizza2.jpg", "http://mooduplabs.com/test/pizza3.jpg" ] }