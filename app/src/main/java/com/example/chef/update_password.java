package com.example.chef;

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

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_password extends AppCompatActivity {
    EditText password, confirmPassword;
    Button updatePassword;
    String passwd, cnfrmPAsswd;
    String phone;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        password = findViewById(R.id.updtPasswd);
        confirmPassword = findViewById(R.id.updtCnfrmPasswd);
        updatePassword = findViewById(R.id.BtnUpdtPasswd);
        phone = getIntent().getStringExtra("phone");

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(update_password.this);
                progressDialog.setMax(100);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                passwd = password.getText().toString();
                cnfrmPAsswd = confirmPassword.getText().toString();

                if (passwd.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(update_password.this, "Password's length should be 6 characters long!", Toast.LENGTH_SHORT).show();
                } else if (!passwd.equals(cnfrmPAsswd)) {
                    progressDialog.dismiss();
                    Toast.makeText(update_password.this, "Password not matched!", Toast.LENGTH_SHORT).show();
                } else {
                    APIinterface apIinterface = myRetro.getretrofit(getApplicationContext()).create(APIinterface.class);
                    apIinterface.updtPasswd(new BigInteger(phone), passwd).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            progressDialog.dismiss();
                            if (!response.body().equals("failed")) {
                                Toast.makeText(update_password.this, "Password changed!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(update_password.this, MainActivity.class));
                            } else {
                                Toast.makeText(update_password.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(update_password.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("gilog", "Update password : " + t.toString());
                        }
                    });
                }
            }
        });
        password.addTextChangedListener(textWatcher);
        confirmPassword.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            passwd = password.getText().toString();
            cnfrmPAsswd = confirmPassword.getText().toString();
            updatePassword.setEnabled(!passwd.isEmpty() && !cnfrmPAsswd.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}