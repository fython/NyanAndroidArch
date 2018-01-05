package moe.feng.arch.demo

import android.content.Context
import moe.feng.common.android.SharedPreferencesProvider

class Settings(context: Context) : SharedPreferencesProvider(context, "settings") {

    val year by intValue(defValue = 2018)

}