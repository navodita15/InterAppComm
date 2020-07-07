package com.example.datasharing;

import android.content.Context;
import android.content.Intent;

public class SendData {
    public SendData(Context context, String action, int firstNum, int secondNum, int result, String pkgName) {

        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
        if (launchIntent != null) {
            launchIntent.putExtra("action", action);
            launchIntent.putExtra("firstNum", firstNum);
            launchIntent.putExtra("secondNum", secondNum);
            launchIntent.putExtra("result", result);
            launchIntent.setAction(pkgName);
            launchIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            context.startActivity(launchIntent);
        }

    }
}
