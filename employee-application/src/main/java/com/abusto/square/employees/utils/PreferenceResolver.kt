package com.abusto.square.employees.utils

import android.content.SharedPreferences

class PreferenceResolver(private val prefs: SharedPreferences) {

    fun putFloat(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }
    fun getFloat(key: String, defaultReturn: Float = 0f) = prefs.getFloat(key, defaultReturn)

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
    fun getInt(key: String, defaultReturn: Int = 0) = prefs.getInt(key, defaultReturn)

    companion object {
        const val SHARED_PREFS_NAME = "Weather.App.Prefs"
        const val KEY_FIVE_DAY_STD_DEV = "KEY_FIVE_DAY_STD_DEV"
        const val KEY_TEMP_MODE = "KEY_TEMP_MODE"
    }
}