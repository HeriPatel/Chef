package com.example.chef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FrgtPasswd extends AppCompatActivity {
    EditText frgtPasswdEmail;
    Button sendEmail;
    String email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgt_passwd);
        frgtPasswdEmail = findViewById(R.id.FrgtPasswdEmail);
        sendEmail = findViewById(R.id.sendEmail);
        frgtPasswdEmail.addTextChangedListener(textWatcher);
        email = frgtPasswdEmail.getText().toString();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String v1 = frgtPasswdEmail.getText().toString();
            sendEmail.setEnabled(!v1.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void sendEmail(View view) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(FrgtPasswd.this, "Check Your mail!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FrgtPasswd.this,MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(FrgtPasswd.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}