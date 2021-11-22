package com.example.chef;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFireBaseMessagingService extends FirebaseMessagingService {

    String title, message;
    public String TAG = "gilog";
    String CHANNEL_ID = "my_notification";
    SharedPreferences sp;

    @Override
    public void onNewToken(String token) {
        /*Log.d(TAG, "Refreshed token: " + token);
        Log.d(TAG, "ID in tokenRefresh : " + Integer.parseInt(sp.getString("chefLoginID", null)));

        APIinterface apIinterface = myRetro.getretrofit(getApplicationContext()).create(APIinterface.class);
        apIinterface.insertKey(token, Integer.parseInt(sp.getString("chefLoginID", null))).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });*/
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        title = remoteMessage.getData().get("Title");
        message = remoteMessage.getData().get("Message");
        notification();
    }

    void notification() {
        createNotificationChannel();

        Intent intent = new Intent(this, homePage.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(404, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my notification";
            String description = "general notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}