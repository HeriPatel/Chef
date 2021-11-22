package com.example.chef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {
    EditText chefEmail, chefPasswd;
    Button loginBtn;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    String email, passwd;
    SharedPreferences sp;
    private static final String SHARED_PREF_NAME = "myPref";
    public static final String KEY_ID = "MainID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        if (sp.getString(KEY_ID, null) != null) {
            Intent intent = new Intent(MainActivity.this, homePage.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);

        chefEmail = findViewById(R.id.chefEmail);
        chefPasswd = findViewById(R.id.chefPasswd);
        loginBtn = findViewById(R.id.chefLogin);
        auth = FirebaseAuth.getInstance();

        chefEmail.addTextChangedListener(loginTextWatcher);
        chefPasswd.addTextChangedListener(loginTextWatcher);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String v1 = chefEmail.getText().toString();
            String v2 = chefPasswd.getText().toString();

            loginBtn.setEnabled(!v1.isEmpty() && !v2.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void loginSuccess() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        email = chefEmail.getText().toString();
        passwd = chefPasswd.getText().toString();

        if (email.equals("") || passwd.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_SHORT).show();
        }

        else {
            retrofit = myRetro.getretrofit(getApplicationContext());
            APIinterface apIinterface = retrofit.create(APIinterface.class);
            Call<String> c = apIinterface.chefLogin(email, passwd);
            c.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    Log.d("gilog", "Res:" + response.body());
                    if (!response.body().equals("error")) {
                        //Firebase
                        auth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString(KEY_ID, response.body());
                                    editor.putString("chefLoginID", response.body());
                                    editor.apply();

                                    Intent intent = new Intent(MainActivity.this, homePage.class);
                                    startActivity(intent);
                                    finish();
                                } else
                                    Toast.makeText(MainActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else
                        Toast.makeText(getApplicationContext(), "Wrong Detail", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressDialog.dismiss();
                    if (t.toString().equals("java.net.SocketTimeoutException"))
                        Toast.makeText(MainActivity.this, "Timeout! Retry!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                    Log.d("gilog", "Error in login : " + t.toString());
                }
            });
        }
    }

    public void chefLogin(View view) {
        loginSuccess();
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, signUp.class);
        startActivity(intent);
    }

    public void FrgtPasswd(View view) {
        startActivity(new Intent(this, FrgtPasswd.class));
    }
}