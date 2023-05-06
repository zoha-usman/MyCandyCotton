package com.zohausman.mycandycotton.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zohausman.mycandycotton.R;
import com.zohausman.mycandycotton.apicontroller;
import com.zohausman.mycandycotton.model.index_response_model;
import com.zohausman.mycandycotton.model.login_response_model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginEmailActivity extends AppCompatActivity {
    TextView login_tv;
    EditText loginemail, loginpassword;
    TextView loginreport;
    Button loginbtn, btnSkipEmailLogin;
    ProgressBar progressbar_Login_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        loginemail= findViewById(R.id.login_email);
        loginpassword= findViewById(R.id.login_password);
        loginreport= findViewById(R.id.login_report);
        loginbtn= findViewById(R.id.login_submit);
        btnSkipEmailLogin=findViewById(R.id.btnSkipEmailLogin);
        login_tv=findViewById(R.id.login_tv);
        progressbar_Login_email= findViewById(R.id.progressbar_Login_email);

        btnSkipEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginEmailActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginEmailActivity.this, RegisterEmailActivity.class);
                startActivity(intent);
            }
        });

        
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginemail.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginEmailActivity.this, "Email and Password required", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        // Show progress bar and hide button
                        progressbar_Login_email.setVisibility(View.VISIBLE);
                        loginbtn.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    processlogin(email, password);
                }


//                processlogin(loginemail.getText().toString(), loginpassword.getText().toString());

            }
        });
//        verifyuserexistence();

    }

    private void verifyuserexistence() {
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        if (sp.contains("username")) {
            Intent intent = new Intent(LoginEmailActivity.this, DashBoard.class);
            startActivity(intent);
        }
    }


    private void processlogin(String email, String password) {

        Call<login_response_model> call= apicontroller.getInstance().getapiSet().getlogin(email, password);
        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
             login_response_model obj = response.body();
             String result= obj.getMessage();
             if(result.equals("exist")){

                 SharedPreferences sp= getSharedPreferences("credentials", MODE_PRIVATE);
                  SharedPreferences.Editor editor=sp.edit();
                  editor.putString("username",email);
                  editor.putString("password",password);
                  editor.commit();
                  editor.apply();

                 Toast.makeText(LoginEmailActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(LoginEmailActivity.this, DashBoard.class);
                 startActivity(intent);
                 finish();


             }
             else if(result.equals("not_exist")){
                 loginreport.setText("Invaild email /password");
                 loginemail.setText("");
                 loginpassword.setText("");
                 try{
                     // Show progress bar and hide button
                    loginbtn.setVisibility(View.VISIBLE);
                     progressbar_Login_email.setVisibility(View.GONE);
                 }catch (Exception e){
                     e.printStackTrace();
                     Toast.makeText(LoginEmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                 }

             }
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
              loginreport.setText("Something went wrong");
            }
        });

    }
//    private void verifyuserexistence() {
//        SharedPreferences sp=getSharedPreferences("credentials", MODE_PRIVATE);
//        if(sp.contains("username"))
//            startActivity(new Intent(getApplicationContext(), DashBoard.class));
//    }
}