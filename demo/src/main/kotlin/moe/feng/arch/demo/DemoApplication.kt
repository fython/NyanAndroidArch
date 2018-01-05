package moe.feng.arch.demo

import android.app.Application
import moe.feng.common.android.initSharedPreferencesProvider

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSharedPreferencesProvider<Settings>()
    }

}