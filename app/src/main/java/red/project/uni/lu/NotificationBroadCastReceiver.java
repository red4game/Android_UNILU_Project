package red.project.uni.lu;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationBroadCastReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My App Notification")
                .setContentText("This is a notification from My App")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create an explicit intent for the MainActivity
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
        builder.setContentIntent(pendingIntent);

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create a notification channel for Android 8.0 and above
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My App Channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
