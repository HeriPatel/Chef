package com.example.chef;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<itemsModel> itemsModelArrayList;
    private TextView status,statusTxt;
    private Button doneAllORder;
    private int chefId;
    private RecyclerView recyclerView;
    private LinearLayout noOrderLayout;

    public orderAdapter(Context context, ArrayList<itemsModel> itemsModelArrayList,TextView status,TextView statusTxt,Button doneAllOrder,int chefId,RecyclerView recyclerView,LinearLayout noOrderLayout) {
        this.context = context;
        this.itemsModelArrayList = itemsModelArrayList;
        this.status = status;
        this.statusTxt = statusTxt;
        this.doneAllORder = doneAllOrder;
        this.chefId = chefId;
        this.recyclerView = recyclerView;
        this.noOrderLayout = noOrderLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemsModel model = itemsModelArrayList.get(position);
        holder.orderItemNotes.setText(model.getItemNotes());
        TextView itemQnty = holder.itemQty;
        itemQnty.setText(model.getQnty());
        holder.itemName.setText(model.getName());
        holder.itemDes.setText(model.getDes());
        holder.itemPrice.setText(model.getPrice() + "/-");
        Glide.with(context)
                .load(context.getString(R.string.url) + model.getImg())
                .centerCrop().placeholder(R.drawable.load)
                .into(holder.itemImg);
        holder.itemDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(view.getRootView().getContext(), R.style.MyAlertDialogStyle);
                progressDialog.setMax(100);
                progressDialog.setMessage("Please wait!");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                APIinterface apIinterface = myRetro.getretrofit(context).create(APIinterface.class);
                apIinterface.doneSingleOrder(model.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        progressDialog.dismiss();
                        Log.d("gilog","in single order done : "+response.body());
                        if(response.body().equals("success")) {
                            itemsModelArrayList.remove(position);
                            notifyDataSetChanged();

                            if(itemsModelArrayList.size() == 0){
                                apIinterface.changeChefStatus(chefId, "Available").enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.d("gilog", "Chef status change : " + response.body());

                                        apIinterface.getDummyOrder().enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if(!response.body().equals("error")) {
                                                    apIinterface.addChef(Integer.parseInt(response.body()), chefId).enqueue(new Callback<String>() {
                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response) {
                                                            apIinterface.loadChefOrder(chefId).enqueue(new Callback<ArrayList<itemsModel>>() {
                                                                @Override
                                                                public void onResponse(Call<ArrayList<itemsModel>> call, Response<ArrayList<itemsModel>> response) {
                                                                    itemsModelArrayList = response.body();
                                                                    Log.d("gilog", "load order res : " + itemsModelArrayList);
                                                                    if (itemsModelArrayList.isEmpty()) {
                                                                        doneAllORder.setEnabled(false);
                                                                    } else {
                                                                        doneAllORder.setEnabled(true);
                                                                        orderAdapter orderAdapter = new orderAdapter(context, itemsModelArrayList, status, statusTxt, doneAllORder, chefId,recyclerView,noOrderLayout);
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
                                                            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                                                            Log.d("gilog", "Error in adding chef id : " + t.toString());
                                                        }
                                                    });
                                                }
                                                else {
                                                    apIinterface.changeChefStatus(chefId, "Available").enqueue(new Callback<String>() {
                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response) {
                                                            itemsModelArrayList.clear();
                                                            /*orderAdapter orderAdapter = new orderAdapter(getApplicationContext(), itemsModelArrayList, status, statustxt, doneAllOrder, chefId);
                                                            recyclerView.setAdapter(orderAdapter);*/

                                                            statusTxt.setText("Available");
                                                            noOrderLayout.setVisibility(View.VISIBLE);
                                                            status.setTextColor(context.getResources().getColor(R.color.green));
                                                            statusTxt.setTextColor(context.getResources().getColor(R.color.green));
                                                            doneAllORder.setEnabled(false);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t) {
                                                            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                                                            Log.d("gilog", "Error in chef status change : " + t.toString());
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                                                Log.d("gilog", "Error in getting dummy order : " + t.toString());
                                            }
                                        });

                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                                        Log.d("gilog", "Error in chef status change : " + t.toString());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d("gilog","Error in single order done : "+t.toString());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice, itemDes;
        private ImageView itemImg;
        private TextView itemQty, orderItemNotes;
        private ImageButton itemDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDes = itemView.findViewById(R.id.itemDes);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImg = itemView.findViewById(R.id.itemImg);
            itemQty = itemView.findViewById(R.id.itemQty);
            itemDone = itemView.findViewById(R.id.doneOrder);
            orderItemNotes = itemView.findViewById(R.id.orderItemNotes);
        }
    }
}