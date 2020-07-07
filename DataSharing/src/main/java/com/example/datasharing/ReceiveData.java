package com.example.datasharing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class ReceiveData {
    public ReceiveData(Context context, String action, int firstNum, int secondNum, int result, String pkgName) {
        final Intent i = new Intent();
        i.putExtra("action",action);
        i.putExtra("firstNum",firstNum);
        i.putExtra("secondNum",secondNum);
        i.putExtra("result",result);
        i.setAction(pkgName);
        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        i.setComponent(new ComponentName("com.example.testapp1","com.example.testapp1.DataBroadcastReceiver"));
        context.sendBroadcast(i);
    }
}
