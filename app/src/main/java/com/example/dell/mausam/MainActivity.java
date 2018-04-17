package com.example.dell.mausam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {


     private EditText location;
     Button btn;
     AlertDialog.Builder ab;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = (EditText)findViewById(R.id.editText);

        btn = (Button)findViewById(R.id.button);


        ab = new AlertDialog.Builder(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.getText().toString().equals(""))
                {

                    ab.setTitle("Error");
                    ab.setMessage("Empty city name....");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = ab.create();
                    dialog.show();

                }else {
                    HandleJSON handleJSON = new HandleJSON(MainActivity.this);
                    handleJSON.execute("mausam", location.getText().toString());
                }
            }
        });



    }

}
