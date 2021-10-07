package com.idanandben.finalapplicationproject.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReciver extends BroadcastReceiver {
    private static boolean screenon;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            screenon=false;
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            screenon=true;
        }

    }
    public static boolean getscreenstate(){
        return screenon;
    }
}
