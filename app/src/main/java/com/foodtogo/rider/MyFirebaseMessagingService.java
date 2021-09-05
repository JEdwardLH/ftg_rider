package com.foodtogo.rider;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.foodtogo.rider.base.AppConstants;
import com.foodtogo.rider.base.BaseApplication;
import com.foodtogo.rider.ui.dashboard.activity.DashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;

/**
 * The type My firebase messaging service.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    /**
     * The Tag.
     */
    String TAG = "FireBase_Message";
    private static String ARG_OPEN = "arg_open";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> hashMap = remoteMessage.getData();
            Log.d(TAG, "Message data payload: " + hashMap.toString());
            String body=hashMap.get("body");
            handleNow(body);
        }
        else if (remoteMessage.getNotification() != null) {
            // Check if message contains a notification payload.
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            handleNow(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

    }
    void handleNow(String messageBody){

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ARG_OPEN, "1");

        Intent[] arrayIntent;
        if (isActivityRunning(DashboardActivity.class)) {
            arrayIntent = new Intent[]{intent};
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BaseApplication.getContext());
            Intent localIntent = new Intent(AppConstants.NEW_ORDER);
            localBroadcastManager.sendBroadcast(localIntent);
        } else {
            arrayIntent = new Intent[]{intent, intent};
        }

        PendingIntent pendingIntent = PendingIntent.getActivities(this,
                0,
                arrayIntent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ding_notification);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_noti_logo)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.mipmap.ic_launcher))
                        .setContentTitle(getString(R.string.fcm_message))
                        .setColor(this.getResources().getColor(R.color.colorOrange))
                        .setContentText(messageBody)
                        .addAction(R.drawable.ic_noti_logo, "New Order", pendingIntent) // #
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(getString(R.string.fcm_message));
        bigTextStyle.bigText(messageBody);
        notificationBuilder.setStyle(bigTextStyle);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/ding_notification");
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();


            String mChannelId = BaseApplication.getContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(mChannelId,getString(R.string.fcm_message), NotificationManager.IMPORTANCE_HIGH);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(messageBody);
            notificationManager.createNotificationChannel(channel);
            channel.setSound(alarmSound,attributes);
            channel.enableLights(true);
            channel.setShowBadge(true);
            notificationBuilder.setChannelId(channelId);
            notificationManager.createNotificationChannel(channel);
        }

        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), defaultSoundUri);
        r.play();
        notificationManager.notify(1, notificationBuilder.build());

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
    public static boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) BaseApplication.getContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }
        return false;
    }
}
