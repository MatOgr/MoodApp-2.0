package com.example.moodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.moodapp.MainActivity.EXTRA_URL;

public class RecipeActivity extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    private RequestQueue mQueue;        //JSON
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mQueue = Volley.newRequestQueue(this);
        jsonParse(EXTRA_URL);

        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDownloadDialog(imageView1);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDownloadDialog(imageView2);
            }
        });
    }


    private void saveImage(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/DCIM");
        String imageName = imageView
                .getContentDescription()
                .toString()
                .replace("https://moodup.team/test/", "");
        File file = new File(dir, imageName);
        OutputStream out;

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            Toast.makeText(RecipeActivity.this, "Image saved in DCIM", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(RecipeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
        }
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
                                    .into((ImageView)findViewById(R.id.imageView1));
                            ((ImageView)findViewById(R.id.imageView1))
                                    .setContentDescription(jsonArray.getString(1)
                                            .replace("http://mooduplabs.com", "https://moodup.team"));

                            Picasso.get()
                                    .load(jsonArray.getString(2)
                                            .replace("http://mooduplabs.com", "https://moodup.team"))
                                    .into((ImageView)findViewById(R.id.imageView2));
                            ((ImageView)findViewById(R.id.imageView2))
                                    .setContentDescription(jsonArray.getString(2)
                                            .replace("http://mooduplabs.com", "https://moodup.team"));

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
        this.finish();
    }


    public void createDownloadDialog(ImageView img) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View imageDownloadView = getLayoutInflater().inflate(R.layout.popup, null);
        Button noButton = imageDownloadView.findViewById(R.id.buttonno);
        Button yesButton = imageDownloadView.findViewById(R.id.buttonyes);
        dialogBuilder.setView(imageDownloadView);
        alertDialog = dialogBuilder.create();


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
                    } else {
                        saveImage(img);
                    }
                }
                alertDialog.dismiss();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    
}