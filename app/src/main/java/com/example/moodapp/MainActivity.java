package com.example.moodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

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



public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "https://moodup.team/test/info.php";
    private RequestQueue mQueue;        //JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//////////////////////////////  JSON
        mQueue = Volley.newRequestQueue(this);
        jsonParseImg(EXTRA_URL);
        ///////////////////////////////////
    }

    // loads pictures from JSON into CircularImageView
    private void jsonParseImg(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("imgs");

                            Picasso.get()
                                    .load(jsonArray.getString(0)
                                            .replace("http://mooduplabs.com", "https://moodup.team"))
                                    .into((CircularImageView) MainActivity.this.findViewById(R.id.circularImg));
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

    public void showOptions(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);

        intent.putExtra("url", EXTRA_URL);
        //this.requestVisibleBehind(true);
        startActivity(intent);
    }

}

//Icons made by <a href="https://www.flaticon.com/authors/kiranshastry" title="Kiranshastry">Kiranshastry</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
//Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>