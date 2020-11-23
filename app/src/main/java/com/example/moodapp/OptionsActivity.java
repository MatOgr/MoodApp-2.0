package com.example.moodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;


public class OptionsActivity extends AppCompatActivity {

    FloatingActionButton fbButton;
    private CallbackManager callbackManager;
    private CircularImageView circularImageView;
    private TextView textView;

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                LoginManager.getInstance().logOut();
                textView.setText("");
                circularImageView.setImageResource(0);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        callbackManager = CallbackManager.Factory.create();
        fbButton = findViewById(R.id.fbButton);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(OptionsActivity.this,
                        Collections.singleton("email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Demo", "Login success");
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Demo", "Login canceled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("Demo", "Login error");
                    }
                });
            }
        });

        circularImageView = findViewById(R.id.fb_photo);
        textView = findViewById(R.id.fb_name);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("Demo", object.toString());
                        try {
                            String name = object.getString("name");
                            String pic = object.getJSONObject("picture")
                                    .getJSONObject("data").getString("url");
                            textView.setText(name);
                            Picasso.get()
                                    .load(pic)
                                    .into(circularImageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "name, id, first_name, last_name, picture.width(150).height(150)");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public void goMain(View view) {
        this.finish();
    }

    public void showRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("url", getIntent().getStringExtra("url"));
        startActivity(intent);
        this.finish();
    }
}