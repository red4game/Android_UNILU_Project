package red.project.uni.lu;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import red.project.uni.lu.lists.ToWatchList.ToWatchDataSource;

public class NotificationWorker extends Worker {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_id";
    private Context context;

    ToWatchDataSource toWatchDataSource;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.toWatchDataSource = new ToWatchDataSource(context);
    }

    @NonNull
    @Override
    public Result doWork() {

        toWatchDataSource.open();
        if (this.toWatchDataSource.ToWatchToday()) {


            // Create a notification builder
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MovieWatcher")
                    .setContentText("You have one or more films to watch today !")
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
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "ToWatchNotify", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        }
        toWatchDataSource.close();
        return Result.success();
    }
}
