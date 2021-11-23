package com.example.chef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class signUp extends AppCompatActivity {
    Retrofit retrofit;
    APIinterface apIinterface;
    EditText chefEmail, chefName, chefPasswd, chefConfrmPasswd, chefPhone;
    ProgressDialog progressDialog;
    Button chefSignUp;
    //FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        chefEmail = findViewById(R.id.Email);
        chefName = findViewById(R.id.Name);
        chefPhone = findViewById(R.id.Phone);
        chefPasswd = findViewById(R.id.Passwd);
        chefConfrmPasswd = findViewById(R.id.CnfrmPasswd);
        chefSignUp = findViewById(R.id.signupBtn);

        //auth = FirebaseAuth.getInstance();

        chefEmail.addTextChangedListener(textWatcher);
        chefName.addTextChangedListener(textWatcher);
        chefPhone.addTextChangedListener(textWatcher);
        chefPasswd.addTextChangedListener(textWatcher);
        chefConfrmPasswd.addTextChangedListener(textWatcher);

        /*if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, homePage.class));
            finish();
        }*/
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String v1 = chefEmail.getText().toString();
            String v2 = chefName.getText().toString();
            String v3 = chefPhone.getText().toString();
            String v4 = chefPasswd.getText().toString();
            String v5 = chefConfrmPasswd.getText().toString();

            chefSignUp.setEnabled(!v1.isEmpty() && !v2.isEmpty() && !v3.isEmpty() && !v4.isEmpty() && !v5.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void SignUp(View view) {
        progressDialog = new ProgressDialog(signUp.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        String email, name, passwd, cnfrmPasswd;
        int phone;
        email = chefEmail.getText().toString();
        name = chefName.getText().toString();
        phone = Integer.parseInt(chefPhone.getText().toString());
        passwd = chefPasswd.getText().toString();
        cnfrmPasswd = chefConfrmPasswd.getText().toString();

        if (name.equals("") || passwd.equals("") || cnfrmPasswd.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_SHORT).show();
        } else if (passwd.length() < 6) {
            progressDialog.dismiss();
            Toast.makeText(this, "Password should be 6 character long", Toast.LENGTH_SHORT).show();
        } else {
            if (passwd.equals(cnfrmPasswd)) {
                //retrofit
                retrofit = myRetro.getretrofit(getApplicationContext());
                apIinterface = retrofit.create(APIinterface.class);
                Call<String> c = apIinterface.chefSignup(email, name, passwd, phone);
                c.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("gilog", response.body());
                        progressDialog.dismiss();
                        if (!response.body().equals("error")) {
                            //Firebase
                            /*auth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {*/
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    /*} else {
                                        Toast.makeText(signUp.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });*/
                        } else
                            Toast.makeText(signUp.this, "Error!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("gilog", "Error in signup : " + t.toString());
                    }
                });
            } else
                Toast.makeText(getApplicationContext(), "Password not matched!", Toast.LENGTH_SHORT).show();
        }
    }
}