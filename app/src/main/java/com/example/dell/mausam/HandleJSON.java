package com.example.dell.mausam;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;



class HandleJSON extends AsyncTask<String, Void , String> {
    String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    String appid = "&appid=b326d0250896b8840d30cb1bdd5fcf80";
    Context context;
    Activity activity;
    AlertDialog.Builder builder;
    ProgressDialog progresDialog;

    public HandleJSON(Context context){
        this.context = context;
        activity = (Activity)context;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(context);
        progresDialog = new ProgressDialog(context);
        progresDialog.setTitle("Please wait...");

        progresDialog.setMessage("connecting to server...");
        progresDialog.setIndeterminate(true);
        progresDialog.setCancelable(false);
        progresDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String mausam = params[0];
        String loaction = params[1];
        String final_url = url1+loaction+appid;

        if(mausam.equals("mausam"))
        {
            try {
                URL url = new URL(final_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

               StringBuilder stringBuilder= new StringBuilder();
                String line = "";
                while((line = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(line+"\n");
                }
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String json) {
        progresDialog.dismiss();
        String[] data;
        try {
                JSONObject jsonObject = new JSONObject(json);
                int code  = Integer.parseInt(jsonObject.getString("cod"));
            if(code == 200) {
                data = new String[13];
                JSONObject coord = jsonObject.getJSONObject("coord");
                JSONObject sys = jsonObject.getJSONObject("sys");
                JSONObject wind = jsonObject.getJSONObject("wind");
                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject clouds = jsonObject.getJSONObject("clouds");

                long sunrl = Long.parseLong(sys.getString("sunrise"));
                long sunsl = Long.parseLong(sys.getString("sunset"));

                Date sr = new Date(sunrl * 1000L);
                Date ss = new Date(sunsl * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                data[0] = coord.getString("lon");
                data[1] = coord.getString("lat");

                data[2] = sys.getString("country");
                data[3] = sdf.format(sr);
                data[4] = sdf.format(ss);

                data[5] = (Float.parseFloat(main.getString("temp_min")) - 273) + " C";
                data[6] = (Float.parseFloat(main.getString("temp_max")) - 273) + " C";
                data[7] = main.getString("pressure") + " Pa";
                data[8] = main.getString("humidity") + " %";

                data[9] = wind.getString("speed") + " m/s";
                data[10] = wind.getString("deg") + " degree";

                data[11] = clouds.getString("all") + " %";
                data[12] = jsonObject.getString("name");


                showDialog("Success...", data);
            }else if(code == 404)
            {
                bugReport("Error", "City not found");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void bugReport(String title, String message) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertD = builder.create();
        alertD.show();
    }

    private void showDialog(String title, String[] data) {
      SharedPreferences sharedPreferences = activity.getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type", title);
        editor.putString("longitude", data[0]);
        editor.putString("latitude", data[1]);
        editor.putString("country", data[2]);
        editor.putString("sunrise", data[3]);
        editor.putString("sunset", data[4]);
        editor.putString("temp_min", data[5]);
        editor.putString("temp_max", data[6]);
        editor.putString("pressure", data[7]);
        editor.putString("humidity", data[8]);
        editor.putString("wind_speed", data[9]);
        editor.putString("wind_degree", data[10]);
        editor.putString("clouds", data[11]);
        editor.putString("name", data[12]);
        editor.commit();
        activity.startActivity(new Intent(context.getApplicationContext(), WeatherReport.class));


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
