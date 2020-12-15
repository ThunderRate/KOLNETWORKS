package com.kolnetworks.koln.fcm;


import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kolnetworks.koln.ui.main.MainActivity;

public class MyFirebaseService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("JJJ", " onMessageReceived : "  );
        if (remoteMessage.getNotification() != null) {
            Log.i("JJJ ","kolNotify : "+remoteMessage.getData().get("kolNotify"));

        }
    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("JJJ "," new token "+s);
    }
}
