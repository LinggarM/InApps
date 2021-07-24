package com.incorps.inapps.utils

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.incorps.inapps.R

object Tools {
    fun showCustomToastSuccess(context: Context, layoutInflater: LayoutInflater, resources: Resources, message: String) {

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

    fun showCustomToastFailed(context: Context, layoutInflater: LayoutInflater, resources: Resources, message: String) {

        //Custom toast
        val customToast: View = layoutInflater.inflate(R.layout.toast_custom, null)
        (customToast.findViewById<View>(R.id.message) as TextView).setTextColor(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.message) as TextView).text = message
        (customToast.findViewById<View>(R.id.icon) as ImageView).setColorFilter(resources.getColor(R.color.white))
        (customToast.findViewById<View>(R.id.icon) as ImageView).setImageResource(R.drawable.ic_baseline_close_24)
        (customToast.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
            resources.getColor(R.color.red_600)
        )

        val toastSuccess = Toast(context)
        toastSuccess.view = customToast
        toastSuccess.duration = Toast.LENGTH_LONG
        toastSuccess.show()
    }

    fun showCustomToastInfo(context: Context, layoutInflater: LayoutInflater, resources: Resources, message: String) {

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
}