package com.zohausman.mycandycotton.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.zohausman.mycandycotton.R;

public class MainActivity extends AppCompatActivity {
    Button btnMobileLog, btnEmailLog, btnSkipLog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEmailLog= findViewById(R.id.btnEmailLog);
        btnMobileLog= findViewById(R.id.btnMobileLog);
        btnSkipLog1= findViewById(R.id.btnSkipLog1);
        btnSkipLog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSkip= new Intent(MainActivity.this, DashBoard.class);
                startActivity(intentSkip);
            }
        });
    btnEmailLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, LoginEmailActivity.class);
                startActivity(intent);
            }
        });

        btnMobileLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, MobileOtpGet.class);
                startActivity(intent);
            }
        });




    }

}