package com.example.datasharing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataReceiver extends BroadcastReceiver {
    private static final String TAG = "DataReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            Log.d(TAG, "onReceive: action == " + intent.getAction());
            int result = intent.getIntExtra("result", -1);
            String action = intent.getStringExtra("action");
            int a = intent.getIntExtra("firstNum", -1);
            int b = intent.getIntExtra("secondNum", -1);
        }
    }
}
