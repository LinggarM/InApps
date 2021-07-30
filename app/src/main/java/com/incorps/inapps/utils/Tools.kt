package com.incorps.inapps.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.incorps.inapps.R
import com.incorps.inapps.preferences.AccountSessionPreferences
import java.text.*
import java.util.Calendar
import java.util.TimeZone
import java.util.Locale

object Tools {
    fun showCustomToastSuccess(
        context: Context,
        layoutInflater: LayoutInflater,
        resources: Resources,
        message: String
    ) {

        val customToast: View = layoutInflater.inflate(R.layout.toast_custom, null)
        (customToast.findViewById<View>(R.id.message) as TextView).setTextColor(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.message) as TextView).text = message
        (customToast.findViewById<View>(R.id.icon) as ImageView).setColorFilter(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.icon) as ImageView).setImageResource(R.drawable.ic_baseline_done_24)
        (customToast.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
            resources.getColor(R.color.green_500)
        )

        val toastSuccess = Toast(context)
        toastSuccess.view = customToast
        toastSuccess.duration = Toast.LENGTH_LONG
        toastSuccess.show()
    }

    fun showCustomToastFailed(
        context: Context,
        layoutInflater: LayoutInflater,
        resources: Resources,
        message: String
    ) {

        //Custom toast
        val customToast: View = layoutInflater.inflate(R.layout.toast_custom, null)
        (customToast.findViewById<View>(R.id.message) as TextView).setTextColor(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.message) as TextView).text = message
        (customToast.findViewById<View>(R.id.icon) as ImageView).setColorFilter(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.icon) as ImageView).setImageResource(R.drawable.ic_baseline_info_24)
        (customToast.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
            resources.getColor(R.color.red_600)
        )

        val toastSuccess = Toast(context)
        toastSuccess.view = customToast
        toastSuccess.duration = Toast.LENGTH_LONG
        toastSuccess.show()
    }

    fun showCustomToastInfo(
        context: Context,
        layoutInflater: LayoutInflater,
        resources: Resources,
        message: String
    ) {

        //Custom toast
        val customToast: View = layoutInflater.inflate(R.layout.toast_custom, null)
        (customToast.findViewById<View>(R.id.message) as TextView).setTextColor(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.message) as TextView).text = message
        (customToast.findViewById<View>(R.id.icon) as ImageView).setColorFilter(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.icon) as ImageView).setImageResource(R.drawable.ic_baseline_info_24)
        (customToast.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
            resources.getColor(R.color.blue_500)
        )

        val toastSuccess = Toast(context)
        toastSuccess.view = customToast
        toastSuccess.duration = Toast.LENGTH_LONG
        toastSuccess.show()
    }

    fun getProductNameById(id: Int): String {
        when (id) {
            101 -> {
                return "Proyektor ≥3000 Lumens"
            }
            102 -> {
                return "Proyektor <3000 Lumens"
            }
            103 -> {
                return "Screen Proyektor"
            }
            104 -> {
                return "Pointer"
            }
            105 -> {
                return "HT (Handy Talky)"
            }
            201 -> {
                return "Banner/ X-Banner/ MMT"
            }
            202 -> {
                return "Poster/ Pamflet/ Leaflet"
            }
            203 -> {
                return "Co-Card"
            }
            204 -> {
                return "Stiker"
            }
            205 -> {
                return "Blocknote"
            }
            206 -> {
                return "Sertifikat"
            }
            207 -> {
                return "Plakat"
            }
            208 -> {
                return "Desain Kemasan"
            }
            301 -> {
                return "Blocknote"
            }
            302 -> {
                return "Plakat"
            }
            303 -> {
                return "Co-Card"
            }
            401 -> {
                return "Install-In"
            }
        }
        return ""
    }

    fun getProductDrawableById(id: Int): Int {
        when (id) {
            101 -> {
                return R.drawable.img_rental_proyektoratastigaribu
            }
            102 -> {
                return R.drawable.img_rental_proyektorbawahtigaribu
            }
            103 -> {
                return R.drawable.img_rental_screenproyektor
            }
            104 -> {
                return R.drawable.img_rental_pointer
            }
            105 -> {
                return R.drawable.img_rental_handytalky
            }
            201 -> {
                return R.drawable.img_cetak_banner
            }
            202 -> {
                return R.drawable.img_cetak_poster
            }
            203 -> {
                return R.drawable.img_cetak_cocard
            }
            204 -> {
                return R.drawable.img_cetak_stiker
            }
            205 -> {
                return R.drawable.img_cetak_blocknote
            }
            206 -> {
                return R.drawable.img_cetak_sertifikat
            }
            207 -> {
                return R.drawable.img_cetak_plakatkayu
            }
            208 -> {
                return R.drawable.img_cetak_desainkemasan
            }
            301 -> {
                return R.drawable.img_cetak_blocknote
            }
            302 -> {
                return R.drawable.img_cetak_plakatkayu
            }
            303 -> {
                return R.drawable.img_cetak_cocard
            }
            401 -> {
                return R.drawable.ic_logo_installin
            }
        }
        return 0
    }

    fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat, Locale("ID"))

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getDateToJakarta(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat, Locale("ID"))

        // Set millis to Jakarta Timezone
        val calJakarta = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        calJakarta.timeInMillis = milliSeconds
        val timezoneJakarta = calJakarta.time.time + TimeZone.getTimeZone("Asia/Jakarta").rawOffset

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
        calendar.timeInMillis = timezoneJakarta
        return formatter.format(calendar.time)
    }

    fun getCurrencySeparator(currency: Long): String {

        var result = ""

        val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols: DecimalFormatSymbols = formatter.decimalFormatSymbols

        symbols.groupingSeparator = '.'
        formatter.decimalFormatSymbols = symbols
        result = formatter.format(currency)

        return result
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        //should check null because in airplane mode it will be null
        return netInfo != null && netInfo.isConnected
    }

    fun isLogin(context: Context): Boolean {
        val accountSessionPreferences = AccountSessionPreferences(context)
        val auth: FirebaseAuth = Firebase.auth
        return accountSessionPreferences.isLogin && (auth.currentUser != null)
    }

    fun countCommaSeparator(isi: String): Int {
        return isi.split(',').filter { i -> i.isNotEmpty() }.size
    }
}