package com.newsapp.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {


    private val prefs: SharedPreferences = context.getSharedPreferences(
        "news_app_prefs",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUser(userId: Int, firstName: String, lastName: String, email: String) {
        prefs.edit().apply {
            putInt(KEY_USER_ID, userId)
            putString(KEY_FIRST_NAME, firstName)
            putString(KEY_LAST_NAME, lastName)
            putString(KEY_EMAIL, email)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getUserId(): Int = prefs.getInt(KEY_USER_ID, -1)

    fun getFirstName(): String? = prefs.getString(KEY_FIRST_NAME, null)

    fun getLastName(): String? = prefs.getString(KEY_LAST_NAME, null)

    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}