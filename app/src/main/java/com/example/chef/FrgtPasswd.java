package com.example.chef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Random;

public class FrgtPasswd extends AppCompatActivity {
    EditText phoneNum;
    Button sendEmail;
    String phone;
    FirebaseAuth auth;
    private static final int REQUEST_SMS_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgt_passwd);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_PERMISSION);
        }

        phoneNum = findViewById(R.id.phoneNum);
        sendEmail = findViewById(R.id.getOtp);
        auth = FirebaseAuth.getInstance();
        phoneNum.addTextChangedListener(textWatcher);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = phoneNum.getText().toString();

                Random rand = new Random();
                int otp = rand.nextInt(899998) + 100001;
                Log.d("gilog","OTP: "+otp);

                /*PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = null;
                mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(getApplicationContext(), "verification completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), "verification failed", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        Toast.makeText(getApplicationContext(), "Code sent", Toast.LENGTH_SHORT).show();
                    }
                };
                FirebaseApp.initializeApp(getApplicationContext());
                auth = FirebaseAuth.getInstance();
                //FirebaseApp.initializeApp(getApplicationContext());
                //phoneNumber = "";
                //Getting intent and PendingIntent instance
                //SmsManager.getDefault().sendTextMessage();
            Intent intent = new Intent(getApplicationContext(), FrgtPasswd.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            //Get the SmsManager instance and call the sendTextMessage method to send message
            SmsManager sms = SmsManager.getDefault();
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                sms.sendTextMessage(phone, null, otp + " is your verification code.", null, null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(FrgtPasswd.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_PERMISSION);
            }
                FirebaseApp.initializeApp(getApplicationContext());*/

                Intent intent1 = new Intent(getApplicationContext(), otp_scrn.class);
                intent1.putExtra("phone",phone);
                startActivity(intent1);
                finish();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String v1 = phoneNum.getText().toString();
            sendEmail.setEnabled(!v1.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}