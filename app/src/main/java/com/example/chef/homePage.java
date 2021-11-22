package com.example.chef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homePage extends AppCompatActivity {
    SharedPreferences sp;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_ID = "ID";
    String chefId;
    TextView status, statustxt;
    Button doneAllOrder;
    ArrayList<itemsModel> itemsModelArrayList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.d("FCMLOG", "fail");
                    Log.w("gilog", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                APIinterface apiService;
                apiService = myRetro.getretrofit(getApplicationContext()).create(APIinterface.class);//"https://gisurat.co.in/JobTest/"
                apiService.insertKey(task.getResult(), Integer.parseInt(chefId)).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(retrofit2.Call<String> call, Response<String> response) {
//                        Log.d("gilog", "token : " + task.getResult());
                    }

                    @Override
                    public void onFailure(retrofit2.Call<String> call, Throwable t) {
                        Log.d("gilog", t.toString());
                    }
                });
            }
        });

        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        chefId = sp.getString("chefLoginID", null);
//        Log.d("gilog", "Id in homePage : " + chefId);

        status = findViewById(R.id.status);
        statustxt = findViewById(R.id.statusTxt);
        recyclerView = findViewById(R.id.orderRV);
        doneAllOrder = findViewById(R.id.doneAllOrder);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        APIinterface apIinterface = myRetro.getretrofit(getApplicationContext()).create(APIinterface.class);
        apIinterface.chefStatus(Integer.parseInt(chefId)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                //Log.d("gilog", "chefStatus res : " + response.body());
                if (!response.body().equals("Error!")) {
                    statustxt.setText(response.body());
                    if (response.body().equals("Available")) {
                        status.setTextColor(getResources().getColor(R.color.green));
                        statustxt.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        status.setTextColor(getResources().getColor(R.color.red));
                        statustxt.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(homePage.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("gilog", "in chef status : " + t.toString());
            }
        });

        apIinterface.loadChefOrder(Integer.parseInt(chefId)).enqueue(new Callback<ArrayList<itemsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<itemsModel>> call, Response<ArrayList<itemsModel>> response) {
                itemsModelArrayList = response.body();
                //Log.d("gilog", "itemmodelArraylist : " + itemsModelArrayList);
                if (itemsModelArrayList.isEmpty()) {
                    doneAllOrder.setEnabled(false);
                } else {
                    doneAllOrder.setEnabled(true);
                    orderAdapter orderAdapter = new orderAdapter(getApplicationContext(), itemsModelArrayList, status, statustxt, doneAllOrder, Integer.parseInt(chefId),recyclerView);
                    recyclerView.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<itemsModel>> call, Throwable t) {
                Log.d("gilog", "Error in fetching order details : " + t.toString());
            }
        });
    }

    public void chefLogOut(View view) {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        FirebaseAuth.getInstance().signOut();

        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

        ProgressDialog progressDialog = new ProgressDialog(view.getRootView().getContext(), R.style.MyAlertDialogStyle);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        APIinterface apIinterface = myRetro.getretrofit(getApplicationContext()).create(APIinterface.class);
        apIinterface.dltFcm(Integer.parseInt(chefId)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                //Log.d("gilog", "in dltFcm : " + response.body());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(homePage.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("gilog", "Error in Delete FCM : " + t.toString());
            }
        });
    }

    public void doneAll(View view) {
        ProgressDialog progressDialog = new ProgressDialog(view.getRootView().getContext(), R.style.MyAlertDialogStyle);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        APIinterface apIinterface = myRetro.getretrofit(getApplicationContext()).create(APIinterface.class);
        apIinterface.doneAllOrder(Integer.parseInt(chefId)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                apIinterface.changeChefStatus(Integer.parseInt(chefId), "Available").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("gilog", "Chef status change : " + response.body());
                        itemsModelArrayList.clear();

                        apIinterface.getDummyOrder().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(!response.body().equals("error")) {
                                    apIinterface.addChef(Integer.parseInt(response.body()), Integer.parseInt(chefId)).enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            apIinterface.loadChefOrder(Integer.parseInt(chefId)).enqueue(new Callback<ArrayList<itemsModel>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<itemsModel>> call, Response<ArrayList<itemsModel>> response) {
                                                    itemsModelArrayList = response.body();
                                                    Log.d("gilog", "load order res : " + itemsModelArrayList);
                                                    if (itemsModelArrayList.isEmpty()) {
                                                        doneAllOrder.setEnabled(false);
                                                    } else {
                                                        doneAllOrder.setEnabled(true);
                                                        orderAdapter orderAdapter = new orderAdapter(getApplicationContext(), itemsModelArrayList, status, statustxt, doneAllOrder, Integer.parseInt(chefId),recyclerView);
                                                        recyclerView.setAdapter(orderAdapter);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<itemsModel>> call, Throwable t) {
                                                    Log.d("gilog", "Error in fetching order details : " + t.toString());
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                            Log.d("gilog", "Error in adding chef id : " + t.toString());
                                        }
                                    });
                                }
                                else {
                                    apIinterface.changeChefStatus(Integer.parseInt(chefId), "Available").enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            itemsModelArrayList.clear();
                                            orderAdapter orderAdapter = new orderAdapter(getApplicationContext(), itemsModelArrayList, status, statustxt, doneAllOrder, Integer.parseInt(chefId),recyclerView);
                                            recyclerView.setAdapter(orderAdapter);

                                            statustxt.setText("Available");
                                            status.setTextColor(getResources().getColor(R.color.green));
                                            statustxt.setTextColor(getResources().getColor(R.color.green));
                                            doneAllOrder.setEnabled(false);
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                            Log.d("gilog", "Error in chef status change : " + t.toString());
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                Log.d("gilog", "Error in getting dummy order : " + t.toString());
                            }
                        });

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("gilog", "Error in chef status change : " + t.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(homePage.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d("gilog", "Error in all order done : " + t.toString());
            }
        });
    }
}