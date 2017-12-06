package com.jianyuyouhun.keepalivedemo.manager

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.jianyuyouhun.keepalivedemo.KeepAliveReceiver
import com.jianyuyouhun.keepalivedemo.app.BaseManager
import com.jianyuyouhun.keepalivedemo.utils.lgD
import com.jianyuyouhun.keepalivedemo.utils.lgI
import com.jianyuyouhun.keepalivedemo.utils.on19orAbove
import java.util.*

/**
 * 进程保活manager
 * Created by wangyu on 2017/12/6.
 */

class KeepAliveManager : BaseManager() {

    companion object {
        val alarmDelay = 10 * 1000L
    }

    lateinit var app: Application
    lateinit var alarmManager: AlarmManager

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            keepAlive()
        }
    }

    override fun onManagerCreate(app: Application) {
        this.app = app
        alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        app.registerReceiver(broadcastReceiver, intentFilter)
        setAlarm()
    }

    private fun setAlarm() {
        lgI("keepAlive", "setAlarm()" + Date().toString())
        val intent = Intent(KeepAliveReceiver.ACTION_KEEP_ALIVE)
        val pendingIntent = PendingIntent.getBroadcast(app, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        on19orAbove(down = {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + alarmDelay, pendingIntent)
        }, up = {
            on19alarm(pendingIntent)
        })
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun on19alarm(pendingIntent: PendingIntent?) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + alarmDelay, pendingIntent)
    }

    fun keepAlive() {
        lgD("keepAlive", "do keepAlive...")
        setAlarm()
    }
}
