package com.jianyuyouhun.keepalivedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jianyuyouhun.keepalivedemo.app.App;
import com.jianyuyouhun.keepalivedemo.manager.KeepAliveManager;

/**
 *
 * Created by wangyu on 2017/12/6.
 */

public class BootAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, KeepAliveService.class));
        KeepAliveManager keepAliveManager = App.Companion.getInstance().getManager(KeepAliveManager.class);
        if (keepAliveManager != null) {
            keepAliveManager.keepAlive();
        }
    }
}
