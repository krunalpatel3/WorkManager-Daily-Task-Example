package krunal.com.example.dailytask;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private SharedPreferences mPreferences;
    private static final String mPreferncesName = "MyPerfernces";
    PeriodicWorkRequest periodicWorkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        mPreferences = getSharedPreferences(mPreferncesName, MODE_PRIVATE);
        String getstatus = mPreferences.getString("Status","No Task Perform");

        textView.setText(getstatus);

        Log.e("getstatus",getstatus);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveData("Frist");

                periodicWorkRequest = new PeriodicWorkRequest.Builder(DailyTask.class,
                        1 , TimeUnit.DAYS)
                        .build();

                // For Setting up Unique PeriodicWork. So there is one PeriodicWork active at a time.
                // If you set new PeriodicWork it will replace old PeriodicWork with new PeriodicWork.
                // In Short it update PeriodicWork.
                // Remember there is Only one PeriodicWork at a time.
                WorkManager.getInstance().enqueueUniquePeriodicWork("DailyTask"
                        ,ExistingPeriodicWorkPolicy.REPLACE
                        ,periodicWorkRequest);


            }
        });



    }

    private void SaveData(String str){
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("Status1", str);
        preferencesEditor.apply();

    }
}
