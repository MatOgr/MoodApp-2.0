package com.example.moodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.moodapp.MainActivity.EXTRA_URL;

public class RecipeActivity extends AppCompatActivity {

    private TextView preparingTitle;
    private RequestQueue mQueue;        //JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mQueue = Volley.newRequestQueue(this);
        jsonParse(EXTRA_URL);

    }

    private void jsonParse(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TextView tmpTV = findViewById(R.id.dishTitle);
                                    //  dish name
                            tmpTV.setText(response.getString("title"));
                            //  dish description
                            tmpTV = findViewById(R.id.dishDescription);
                            tmpTV.setText(response.getString("description"));


                            //  ingredients
                            JSONArray jsonArray = response.getJSONArray("ingredients");
                            tmpTV = findViewById(R.id.ingredientsDescription);
                            tmpTV.setText("");
                            for(int i = 0; i < jsonArray.length() - 1; i++) {
                                tmpTV.append("- " + jsonArray.getString(i) + "\n");
                            }
                            tmpTV.append("- " + jsonArray.getString(jsonArray.length()-1));

                            //  preparing
                            tmpTV = findViewById(R.id.preparingDescription);
                            tmpTV.setText("");
                            jsonArray = response.getJSONArray("preparing");
                            for(int i = 0; i < jsonArray.length() - 1; i++) {
                                tmpTV.append(String.format("%d. %s\n\n", i + 1, jsonArray.getString(i)));
                            } tmpTV.append(String.format("%d. %s", jsonArray.length(), jsonArray.getString(jsonArray.length()-1)));

                            // images
                            jsonArray = response.getJSONArray("imgs");

                            Picasso.get()
                                    .load(jsonArray.getString(1)
                                            .replace("http://mooduplabs.com", "https://moodup.team"))
                                    .into((ImageView)findViewById(R.id.imageButton));
                            Picasso.get()
                                    .load(jsonArray.getString(2)
                                            .replace("http://mooduplabs.com", "https://moodup.team"))
                                    .into((ImageView)findViewById(R.id.imageButton2));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    // TODO: 20.11.2020 downloading image
    public void downloadImage(){

    }

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }
}