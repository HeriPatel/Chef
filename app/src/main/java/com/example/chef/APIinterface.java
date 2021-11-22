package com.example.chef;

import android.widget.EditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIinterface {
    @POST("chef_details.php")
    Call<String> chefLogin(@Query("chefEmail") String chefEmail, @Query("chefPasswd") String chefPasswd);

    @POST("chef_insert_data.php")
    Call<String> chefSignup(@Query("chefEmail") String chefEmail, @Query("chefName") String chefName, @Query("chefPasswd") String chefPasswd, @Query("chefPhone") int chefPhone);

    @POST("dltFcm.php")
    Call<String> dltFcm(@Query("id") int id);

    @POST("load_chef_order.php")
    Call<ArrayList<itemsModel>> loadChefOrder(@Query("id") int id);

    @POST("chef_status.php")
    Call<String> chefStatus(@Query("id") int id);

    @POST("done_order.php")
    Call<String> doneSingleOrder(@Query("id") int id);

    @POST("done_all_order.php")
    Call<String> doneAllOrder(@Query("chef_id") int chef_id);

    @POST("change_chef_status.php")
    Call<String> changeChefStatus(@Query("id") int id,@Query("status") String status);

    @POST("get_dummy_order.php")
    Call<String> getDummyOrder();

    @POST("addChef.php")
    Call<String> addChef(@Query("id") int id, @Query("chef_id") int chef_id);

    @Headers("Content-Type: application/json")
    @GET("insert_key.php")
    Call<String> insertKey(@Query("key") String key, @Query("id") int id);
}