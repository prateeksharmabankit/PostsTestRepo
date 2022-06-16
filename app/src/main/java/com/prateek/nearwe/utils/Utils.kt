/*************************************************
 * Created by Efendi Hariyadi on 13/06/22, 12:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/06/22, 12:00 PM
 ************************************************/

package com.prateek.nearwe.utils

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


public  class Utils {

    class CompanionClass {


        companion object {
            fun ConvertTimeStampintoAgo(timeStamp: Long?): String? {
                try {
                    val cal = Calendar.getInstance(Locale.getDefault())
                    cal.timeInMillis = timeStamp!!
                    val date = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString()
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val dateObj: Date = sdf.parse(date)
                    val p = PrettyTime()
                    return p.format(dateObj)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                return ""
            }
            fun hideKeyboard(view: View,context: Context?) {
                val inputMethodManager = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            fun getAddressFromLatLng(context: Context?, latitude:String, longitude:String): String {
                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(context, Locale.getDefault())
                return try {
                    addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
                    addresses[0].getAddressLine(0)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            }
            fun getRandomString(length: Int) : String {
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                return (1..length)
                    .map { allowedChars.random() }
                    .joinToString("")
            }
        }
    }
}