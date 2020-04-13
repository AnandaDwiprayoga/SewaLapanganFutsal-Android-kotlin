package org.z1god.selap.Utilitis

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MyPreferences {
    companion object {
        private const val SHARED_NAME: String = "myPreferences"
        private const val KEY_LOGIN: String = "key_login"
        private const val KEY_FIRST_OPEN_APP: String = "key_open_apps"

        private lateinit var sharedPreferences: SharedPreferences

        public fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME, MODE_PRIVATE)
        }

        public fun getIsLogin(): Boolean {
            return sharedPreferences.getBoolean(KEY_LOGIN, false)
        }

        public fun setIsLogin() {
            sharedPreferences.edit().putBoolean(KEY_LOGIN, true).apply()
        }

        public fun getIsFinishOnBoarding(): Boolean {
            return sharedPreferences.getBoolean(KEY_FIRST_OPEN_APP, false)
        }

        public fun setIsFinishOnBoarding() {
            sharedPreferences.edit().putBoolean(KEY_FIRST_OPEN_APP, true).apply()
        }
    }

}