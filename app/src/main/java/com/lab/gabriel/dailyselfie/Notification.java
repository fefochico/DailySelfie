package com.lab.gabriel.dailyselfie;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fefochico on 29/11/2015.
 */
public class Notification  {
    private final int MY_NOTIFICATION_ID = 1;
    Context mContext;
    Intent mNotificationIntent;
    PendingIntent pendingIntent;
    public Notification(Context context) {
        mContext= context;

        mNotificationIntent = new Intent(mContext, MainActivity.class);
        mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        pendingIntent = PendingIntent.getActivity(mContext, 0, mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void activate(){
        android.app.Notification.Builder notificationBuilder = new android.app.Notification.Builder(mContext)
                .setContentTitle("Time to take a photo")
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        // TODO: Send the notification
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
    }
}
