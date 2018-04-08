package com.example.mana.nexo_app.utils;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.example.mana.nexo_app.R;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by MANA on 2/7/2018.
 */

    public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle(title)
                .setContentText(body);
        int NOTIFICATION_ID= (int) System.currentTimeMillis();
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID,notification.build());
    }
}
