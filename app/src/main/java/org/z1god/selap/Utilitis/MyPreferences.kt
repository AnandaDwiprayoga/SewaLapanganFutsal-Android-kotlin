package org.z1god.selap.Utilitis

import android.accounts.Account
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MyPreferences {
    companion object {
        private const val SHARED_NAME: String = "myPreferences"
        private const val KEY_LOGIN: String = "key_login"
        private const val KEY_FIRST_OPEN_APP: String = "key_open_apps"
        private const val KEY_NAME_ACCOUNT : String = "KEY_NAME_ACCOUNT"
        private const val KEY_EMAIL_ACCOUNT : String = "KEY_EMAIL_ACCOUNT"
        private const val KEY_PASSWORD_ACCOUNT : String = "KEY_PASSWORD_ACCOUNT"
        private const val KEY_IMG_ACCOUNT : String = "KEY_IMG_ACCOUNT"

        private lateinit var sharedPreferences: SharedPreferences

        fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences(SHARED_NAME, MODE_PRIVATE)
        }

        fun getIsLogin(): Boolean {
            return sharedPreferences.getBoolean(KEY_LOGIN, false)
        }

        fun setIsLogin() {
            sharedPreferences.edit().putBoolean(KEY_LOGIN, true).apply()
        }

        fun getIsFinishOnBoarding(): Boolean {
            return sharedPreferences.getBoolean(KEY_FIRST_OPEN_APP, false)
        }

        fun setIsFinishOnBoarding() {
            sharedPreferences.edit().putBoolean(KEY_FIRST_OPEN_APP, true).apply()
        }

        fun setNameAccount(name: String){
            sharedPreferences.edit().putString(KEY_NAME_ACCOUNT, name).apply()
        }

        fun getNameAccount() : String? {
            return sharedPreferences.getString(KEY_NAME_ACCOUNT, "User")
        }

        fun setEmailAccount(email: String){
            sharedPreferences.edit().putString(KEY_EMAIL_ACCOUNT, email).apply()
        }

        fun getEmailAccount() : String? {
           return sharedPreferences.getString(KEY_EMAIL_ACCOUNT, "")
        }


        fun setImgAccount(imgUrl: String){
            sharedPreferences.edit().putString(KEY_IMG_ACCOUNT, imgUrl).apply()
        }

        fun getImgAccount() : String? {
            return sharedPreferences.getString(KEY_IMG_ACCOUNT, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ4r3-c0W9lb_kVK6q6JjK_1M93l9shOw0ZVrMLz-SFtIZ9e63D&usqp=CAU")
        }
    }

}