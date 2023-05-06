package com.zohausman.mycandycotton.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zohausman.mycandycotton.R;
import com.zohausman.mycandycotton.apicontroller;
import com.zohausman.mycandycotton.model.index_response_model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEmailActivity extends AppCompatActivity {
    EditText regemail,regpassword;
    Button regsubmit;
    TextView tv, accountHave;
    ProgressBar  progressbar_Register_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

        regemail=findViewById(R.id.reg_email);

        regpassword=(EditText)findViewById(R.id.reg_password);
        tv=findViewById(R.id.signup_report_tv);
        progressbar_Register_email=findViewById(R.id.progressbar_Register_email);
        accountHave= findViewById(R.id.accountHave);

        regsubmit=(Button)findViewById(R.id.reg_submit);


        accountHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterEmailActivity.this, LoginEmailActivity.class);
                startActivity(intent);
            }
        });
        regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = regemail.getText().toString().trim();
                String password = regpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterEmailActivity.this, "Email and Password required", Toast.LENGTH_SHORT).show();
                } else {
                    // Show progress bar and hide button
                    progressbar_Register_email.setVisibility(View.VISIBLE);
                    regsubmit.setVisibility(View.GONE);

                    userRegister(email, password);
                }
//                try{
//
//                    // Show progress bar and hide button
//                    progressbar_Register_email.setVisibility(View.VISIBLE);
//                    regsubmit.setVisibility(View.GONE);
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(RegisterEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                try{
//                    userregister(regemail.getText().toString(),regpassword.getText().toString());
//                    Intent intent=new Intent(RegisterEmailActivity.this, LoginEmailActivity.class);
//                    startActivity(intent);
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(RegisterEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }


            }
        });
    }

    public void userRegister(String email, String password)
    {
        String name="not applicable";

        Call<index_response_model> call=apicontroller.getInstance().getapiSet().getregister(name, email, password);

        call.enqueue(new Callback<index_response_model>() {
            @Override
            public void onResponse(Call<index_response_model> call, Response<index_response_model> response) {
                index_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("inserted"))
                {
                    tv.setText("Successfully Registered");
                    tv.setTextColor(Color.GREEN);
                    regemail.setText("");
                }
                if(result.equals("exist"))
                {
                    tv.setText("Sorry...! You are already registered");
                    tv.setTextColor(Color.RED);
                    regemail.setText("");
                    regpassword.setText("");
                }
                // Hide progress bar and show button
                progressbar_Register_email.setVisibility(View.GONE);
                regsubmit.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<index_response_model> call, Throwable t) {
                tv.setText("Something went wrong");
                tv.setTextColor(Color.RED);
                regemail.setText("");
                regpassword.setText("");
                // Hide progress bar and show button
                progressbar_Register_email.setVisibility(View.GONE);
                regsubmit.setVisibility(View.VISIBLE);
            }
        });
    }
}

