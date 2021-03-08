package com.example.servicefinality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.servicefinality.database.ApplicationPath;
import com.example.servicefinality.database.RoomDB;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ApplicationPath mPath;
    private RoomDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ONCREATE","STARTED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        db= Room.databaseBuilder(this,RoomDB.class,"Sample.bd").allowMainThreadQueries().build();

        if (db.dao().getPath()==null){
            mPath = new ApplicationPath("no package");
            db.dao().insertPackage(mPath);
        }
        else {
            mPath = db.dao().getPath();
        }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new StartFragment(db))
                    .commitAllowingStateLoss();
        checkIntent(getIntent());

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(MyService.ACTION_FILTER);
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //do something
            String msg = intent.getStringExtra("text_for_alert");

            Log.e("LOGS", "Message from BroadcastReceiver: " + msg);

           NotificationDialog dialog=new NotificationDialog(db);
           dialog.setCancelable(false);

           dialog.show(getSupportFragmentManager(),null);

            abortBroadcast(); //отменяет дальнейшую обработку бродкаста
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }


    public void checkIntent(Intent intent) {
        if (intent.hasExtra("click_action")) {
            IntentHelper.startIntent(this,db);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
    }

    public static class MyService extends FirebaseMessagingService {
        public MyService() {}

        public static final String ACTION_FILTER  = "dialog";

        //private RoomDB db= Room.databaseBuilder(this,RoomDB.class,"Sample.bd").allowMainThreadQueries().build();

        @Override
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

            Map<String, String> data = remoteMessage.getData();
            if (data.containsKey("click_action")) {
                final String remMesBody = remoteMessage.getNotification().getBody();
                Log.d("SERVICE", "Message Notification Body: " + remoteMessage.getNotification().getBody());

                Intent intent = new Intent(ACTION_FILTER);
                intent.putExtra("text_for_alert",remMesBody);
                sendOrderedBroadcast(intent, null);
            }
        }
    }

}