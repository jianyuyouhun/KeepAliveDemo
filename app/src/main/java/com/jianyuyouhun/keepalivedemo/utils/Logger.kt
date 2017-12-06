package com.jianyuyouhun.keepalivedemo.utils

import android.util.Log
import com.jianyuyouhun.keepalivedemo.app.App

/**
 * 日志打印
 * Created by wangyu on 2017/7/25.
 */
private inline fun doLog(log: () -> Unit) {
    if (App.isDebug) log()
}

fun lgE(tag: String, msg: String) = doLog { Log.e(tag, msg) }

fun lgI(tag: String, msg: String) = doLog { Log.i(tag, msg) }

fun lgD(tag: String, msg: String) = doLog { Log.d(tag, msg) }

fun lgW(tag: String, msg: String) = doLog { Log.w(tag, msg) }
