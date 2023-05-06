package com.zohausman.mycandycotton.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.zohausman.mycandycotton.R;

import java.util.concurrent.TimeUnit;

public class MobileOtpVerify extends AppCompatActivity {
    EditText Et_Mobile_otp_vfy;
    Button btn_verify;
    String phonenumber;
    String otpid;
    ProgressBar progressbar_verify_otp_btn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp_verify);

        phonenumber=getIntent().getStringExtra("mobile").toString();
        Et_Mobile_otp_vfy=(EditText)findViewById(R.id.Et_Mobile_otp_vfy);
        btn_verify=(Button)findViewById(R.id.btn_verify);
        progressbar_verify_otp_btn=(ProgressBar)findViewById(R.id.progressbar_verify_otp_btn) ;
        mAuth=FirebaseAuth.getInstance();
       //
        initiateotp();

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    // Show progress bar and hide button
                    progressbar_verify_otp_btn.setVisibility(View.VISIBLE);
                    btn_verify.setVisibility(View.GONE);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MobileOtpVerify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }



                if(Et_Mobile_otp_vfy.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                else if(Et_Mobile_otp_vfy.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"INvalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otpid,Et_Mobile_otp_vfy.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }
    //firebase mobile authentication

    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                });        // OnVerificationStateChangedCallbacks
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(MobileOtpVerify.this,MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"SignIn Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}