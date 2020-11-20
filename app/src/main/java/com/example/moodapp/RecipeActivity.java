package com.example.moodapp;

import androidx.annotation.NonNull;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.example.moodapp.MainActivity.EXTRA_URL;

public class RecipeActivity extends AppCompatActivity {

    private RequestQueue mQueue;        //JSON
    Button savebtn;
    ImageView imageView;

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mQueue = Volley.newRequestQueue(this);
        jsonParse(EXTRA_URL);


        imageView = findViewById(R.id.imageView1);
        savebtn = findViewById(R.id.saveButton1);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
                    } else {
                        saveImage();
                    }
                }
            }
        });
    }


    private void saveImage() {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path+"/DCIM");
        String imagename = time + ".PNG";
        File file = new File(dir, imagename);
        OutputStream out;

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
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
                            Picasso.get()
                                    .load(jsonArray.getString(2)
                                            .replace("http://mooduplabs.com", "https://moodup.team"))
                                    .into((ImageView)findViewById(R.id.imageView2));

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


//    public void downloadImage(ImageButton imageView){
//        BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//
//        File filepath = Environment.getExternalStorageDirectory();
//        File dir = new File(filepath.getAbsolutePath()+"/MoodApp/");
//        dir.mkdir();
//        File file = new File(dir, System.currentTimeMillis() + ".jpg");
//        try {
//            outputStream = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        Toast.makeText(getApplicationContext(), "Image saved to ./MoodApp ^^",
//                Toast.LENGTH_SHORT). show();
//    }

    public void goMain(View view) {
        //this.getParent().findViewById(R.id.optionsButton).setVisibility(View.VISIBLE);
        this.finish();
    }



    
    
}