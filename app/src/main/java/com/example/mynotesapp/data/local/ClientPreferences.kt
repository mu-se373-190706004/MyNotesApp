package com.example.mynotesapp.data.local

import android.content.Context

/**
 *Created by Mert Melih Aytemur on 22.06.2022.
 */
class ClientPreferences(context: Context) {
    companion object{
        private const val PREFERENCES_NAME = "User"
        private const val USERNAME = "Username"
        private const val REMEMBER_ME = "RememberMe"
    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE)
    }

    fun setUsername(userEmail : String){
        with(sharedPreferences.edit()){
            userEmail.let {
                putString(USERNAME,it)
                apply()
            }
        }
    }

    fun getUsername() = sharedPreferences.getString(USERNAME,"")

    fun clearSharedPref(){
        with(sharedPreferences.edit()){
            clear()
            apply()
        }
    }


    fun setRememberMe(state : Boolean){
        with(sharedPreferences.edit()){
            putBoolean(REMEMBER_ME,state)
            apply()
        }
    }

    fun isRememberMe() = sharedPreferences.getBoolean(REMEMBER_ME,false)
}