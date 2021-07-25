package com.incorps.inapps.preferences

import android.content.Context
import android.content.SharedPreferences

class AccountSessionPreferences (context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private var editor: SharedPreferences.Editor = sharedPref.edit()

    companion object {
        private const val PREF_NAME = "AccountSession"
        private const val PRIVATE_MODE = 0
        private const val IS_LOGIN = "IsLogin"
        private const val ID_USER = "IdUser"
        private const val EMAIL_USER = "EmailUser"
        private const val NAME_USER = "NameUser"
        private const val PHONE_USER = "PhoneUser"
        private const val ADDRESS_USER = "AddressUser"
    }

    var isLogin: Boolean
        get() = sharedPref.getBoolean(IS_LOGIN, false)
        set(isLogin) {
            editor.putBoolean(IS_LOGIN, isLogin)
            editor.commit()
        }
    var idUser: String
        get() = sharedPref.getString(ID_USER, "").toString()
        set(id) {
            editor.putString(ID_USER, id)
            editor.commit()
        }
    var emailUser: String
        get() = sharedPref.getString(EMAIL_USER, "").toString()
        set(email) {
            editor.putString(EMAIL_USER, email)
            editor.commit()
        }
    var nameUser: String
        get() = sharedPref.getString(NAME_USER, "").toString()
        set(name) {
            editor.putString(NAME_USER, name)
            editor.commit()
        }
    var phoneUser: String
        get() = sharedPref.getString(PHONE_USER, "").toString()
        set(phone) {
            editor.putString(PHONE_USER, phone)
            editor.commit()
        }
    var addressUser: String
        get() = sharedPref.getString(ADDRESS_USER, "").toString()
        set(address) {
            editor.putString(ADDRESS_USER, address)
            editor.commit()
        }

    fun logoutAccount() {
        editor.clear()
        editor.commit()
    }
}