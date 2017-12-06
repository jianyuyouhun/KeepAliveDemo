package com.jianyuyouhun.keepalivedemo.app

import android.app.Application
import com.jianyuyouhun.keepalivedemo.BuildConfig
import com.jianyuyouhun.keepalivedemo.app.exception.ExceptionCaughtAdapter
import com.jianyuyouhun.keepalivedemo.manager.KeepAliveManager
import com.jianyuyouhun.keepalivedemo.utils.lgE
import com.jianyuyouhun.permission.library.EZPermission

/**
 *
 * Created by wangyu on 2017/12/6.
 */

class App : Application() {

    companion object {
        val TAG: String = "KTApp"
        var isDebug: Boolean = false
        lateinit var instance: App
            private set
    }

    private var managerMap: MutableMap<String, BaseManager> = HashMap()

    override fun onCreate() {
        super.onCreate()
        instance = this
        isDebug = setDebugMode()
        initExceptionCatch()
        initDependencies()
        initApp()
    }

    /**
     * 初始化异常捕获逻辑
     */
    private fun initExceptionCatch() {
        if (isDebug) {
            val handler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            val exceptionCaughtAdapter = ExceptionCaughtAdapter(handler)
            Thread.setDefaultUncaughtExceptionHandler(exceptionCaughtAdapter)
        }
    }

    /**
     * 设置调试模式参数
     */
    private fun setDebugMode(): Boolean = BuildConfig.DEBUG

    private fun initDependencies() {
        EZPermission.init(this)
    }

    private fun initApp() {
        val managers: ArrayList<BaseManager> = ArrayList()
        initManagers(managers)
        for (manager in managers) {
            val time = System.currentTimeMillis()
            manager.onManagerCreate(this)
            val managerClass = manager.javaClass
            val managerName = managerClass.name
            managerMap.put(managerName, manager)
            val spendTime = System.currentTimeMillis() - time
            lgE(TAG, managerClass.simpleName + "启动耗时(毫秒)：" + spendTime)
        }
        for (manager in managers) {
            manager.onAllManagerCreated()
        }
    }

    private fun initManagers(managers: ArrayList<BaseManager>) {
        managers.add(KeepAliveManager())
    }

    @Suppress("UNCHECKED_CAST")
    fun <Manager : BaseManager> getManager(manager: Class<Manager>): Manager? = getManager(manager.name)

    @Suppress("UNCHECKED_CAST")
    fun <Manager : BaseManager> getManager(modelName: String): Manager? = managerMap[modelName] as Manager
}
