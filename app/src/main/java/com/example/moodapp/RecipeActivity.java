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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.moodapp.MainActivity.EXTRA_URL;

public class RecipeActivity extends AppCompatActivity {

    private TextView preparingTitle;
    private RequestQueue mQueue;        //JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mQueue = Volley.newRequestQueue(this);
        jsonParseImgs(EXTRA_URL);

    }

    private void jsonParseImgs(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("imgs");

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

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }
}