package com.jianyuyouhun.keepalivedemo.app

import android.app.Application

/**
 * 管理器
 * Created by wangyu on 2017/12/6.
 */

abstract class BaseManager {

    /**
     * 管理器被初始化的回调，初始化整个管理器
     */
    abstract fun onManagerCreate(app: Application)

    /**
     * 所有管理器都初始化后执行
     */
    fun onAllManagerCreated() {

    }

}
