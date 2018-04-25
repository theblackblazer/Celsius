package com.example.dell.mausam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class WeatherReport extends AppCompatActivity {
   EditText lon, lat, sunr,suns, min_temp, max_temp, press, humidity, speed, direction, clouds;
    private AdView fAdView, sAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);

        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);

        lon = (EditText)findViewById(R.id.lon);
        lat = (EditText)findViewById(R.id.lat);
        sunr = (EditText)findViewById(R.id.sun_rise);
        suns = (EditText)findViewById(R.id.sunset);
        min_temp = (EditText)findViewById(R.id.min_temp);
        max_temp = (EditText)findViewById(R.id.max_temp);
        press  = (EditText)findViewById(R.id.pressure);
        humidity = (EditText)findViewById(R.id.humidity);
        speed = (EditText)findViewById(R.id.speed);
        direction = (EditText)findViewById(R.id.direction);
        clouds = (EditText)findViewById(R.id.clouds);


        lon.setText(sharedPreferences.getString("longitude", "null"));
        lat.setText(sharedPreferences.getString("latitude", "null"));

        sunr.setText(sharedPreferences.getString("sunrise", "null"));
        suns.setText(sharedPreferences.getString("sunset", "null"));

        min_temp.setText(sharedPreferences.getString("temp_min", "null"));
        max_temp.setText(sharedPreferences.getString("temp_max", "null"));

        press.setText(sharedPreferences.getString("pressure", "null"));
        humidity.setText(sharedPreferences.getString("humidity", "null"));

        speed.setText(sharedPreferences.getString("wind_speed", "null"));
        direction.setText(sharedPreferences.getString("wind_degree", "null"));
        clouds.setText(sharedPreferences.getString("clouds", "null"));


    }
}
