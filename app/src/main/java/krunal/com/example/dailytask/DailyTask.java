package krunal.com.example.dailytask;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.MODE_PRIVATE;

public class DailyTask extends Worker {

    private SharedPreferences mPreferences;

    private static final String mPreferncesName = "MyPerfernces";
//    public static final String EXTRA_TEXT = "FirstTime";

    Context context;

    public DailyTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        mPreferences = context.getSharedPreferences(mPreferncesName, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Result doWork() {

//        String text = getInputData().getString(EXTRA_TEXT);
//        Log.e("text",text);

        String getstatus = mPreferences.getString("Status1","No Task Perform");
        if (!getstatus.equalsIgnoreCase("Frist")){

            mPreferences = context.getSharedPreferences(mPreferncesName, MODE_PRIVATE);
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putString("Status", Utility.getCurruntDateTime());
            preferencesEditor.apply();
            sendNotification("Daily Notification", "Second " + Utility.getCurruntDateTime());
            Log.e("currentTime", Utility.getCurruntDateTime());

        }else {
            Log.e("chack","Not Frist");
            sendNotification("Notification First time","Nothing is save in SharedPreferences");
            SaveData("Second");
        }


        return Result.SUCCESS;
    }

    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }

    private void SaveData(String str){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("Status1", str);
        preferencesEditor.apply();

    }
}
