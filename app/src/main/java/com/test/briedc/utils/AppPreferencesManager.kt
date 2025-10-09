package com.test.briedc.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object AppPreferencesManager {

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(GlobalUtils.PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    // ---------- String ----------
    fun setString(context: Context, key: String, value: String) {
        getPrefs(context).edit { putString(key, value) }
    }
    fun getString(context: Context, key: String, default: String = ""): String {
        return getPrefs(context).getString(key, default) ?: default
    }

    // ---------- Int ----------
    fun setInt(context: Context, key: String, value: Int) {
        getPrefs(context).edit { putInt(key, value) }
    }
    fun getInt(context: Context, key: String, default: Int = 0): Int {
        return getPrefs(context).getInt(key, default)
    }

    // ---------- Boolean ----------
    fun setBoolean(context: Context, key: String, value: Boolean) {
        getPrefs(context).edit { putBoolean(key, value) }
    }
    fun getBoolean(context: Context, key: String, default: Boolean = false): Boolean {
        return getPrefs(context).getBoolean(key, default)
    }

    // ---------- Float ----------
    fun setFloat(context: Context, key: String, value: Float) {
        getPrefs(context).edit { putFloat(key, value) }
    }
    fun getFloat(context: Context, key: String, default: Float = 0f): Float {
        return getPrefs(context).getFloat(key, default)
    }

    // ---------- Long ----------
    fun setLong(context: Context, key: String, value: Long) {
        getPrefs(context).edit { putLong(key, value) }
    }
    fun getLong(context: Context, key: String, default: Long = 0L): Long {
        return getPrefs(context).getLong(key, default)
    }

    // ---------- Remove / Clear ----------
    fun remove(context: Context, key: String) {
        getPrefs(context).edit { remove(key) }
    }
    fun clear(context: Context) {
        getPrefs(context).edit { clear() }
    }
}