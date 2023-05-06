package com.zohausman.mycandycotton.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.zohausman.mycandycotton.R;

public class MobileOtpGet extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText Et_Mobile_No;
    ProgressBar progressbar_verify_otp;
    Button btn_GetOpt, btnSkipMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp_get);


        Et_Mobile_No=(EditText)findViewById(R.id.Et_Mobile_No);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(Et_Mobile_No);
        btn_GetOpt=(Button)findViewById(R.id.btn_GetOpt);
        progressbar_verify_otp=(ProgressBar)findViewById(R.id.progressbar_verify_otp) ;
        btnSkipMobile= (Button) findViewById(R.id.btnSkipMobile);


        btn_GetOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNo = Et_Mobile_No.getText().toString().trim();

                // check if the mobile number field is empty
                if (mobileNo.isEmpty()) {
                    Toast.makeText(MobileOtpGet.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check if the mobile number is valid
                if (!ccp.isValidFullNumber()) {
                    Toast.makeText(MobileOtpGet.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    // Show progress bar and hide button
                    progressbar_verify_otp.setVisibility(View.VISIBLE);
                    btn_GetOpt.setVisibility(View.GONE);
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(MobileOtpGet.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }


                Intent intent=new Intent(MobileOtpGet.this,MobileOtpVerify.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });
        btnSkipMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMobSkip= new Intent(MobileOtpGet.this, DashBoard.class);
            startActivity(intentMobSkip);}
        });

    }
}