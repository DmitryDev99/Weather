package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.weather.catApi.presentation.MainActivity;

public class MainActivity2 extends AppCompatActivity {

    private static final String TagActivity = "TagMainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.textVie).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        Log.d(TagActivity, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TagActivity, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TagActivity, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TagActivity, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TagActivity, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TagActivity, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TagActivity, "onDestroy");
    }
}