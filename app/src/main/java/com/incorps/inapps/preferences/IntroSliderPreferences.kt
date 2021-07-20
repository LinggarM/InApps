package com.incorps.inapps.preferences

import android.content.Context
import android.content.SharedPreferences

class IntroSliderPreferences (context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private var editor: SharedPreferences.Editor = sharedPref.edit()

    companion object {
        private const val PREF_NAME = "IntroSlider"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        private const val PRIVATE_MODE = 0
    }

    var isFirstTimeLaunch: Boolean
        get() = sharedPref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }
}