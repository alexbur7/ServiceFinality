package com.example.servicefinality;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.example.servicefinality.database.RoomDB;

public class IntentHelper {

    public static void startIntent(Context context, RoomDB db){
        Log.e("PACKAGE",db.dao().getPath().getPackageName());

        Intent packIntent=context.getPackageManager().getLaunchIntentForPackage(db.dao().getPath().getPackageName());
        try {

            if (context.getPackageManager().queryIntentActivities(packIntent, PackageManager.MATCH_DEFAULT_ONLY).isEmpty()) {
                Toast.makeText(context, R.string.wrong_pack, Toast.LENGTH_SHORT).show();
            } else {
                context.startActivity(packIntent);
            }
        } catch (Exception e){
            Toast.makeText(context, R.string.wrong_pack, Toast.LENGTH_SHORT).show();
        }
    }

}
