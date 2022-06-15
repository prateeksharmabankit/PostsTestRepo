/*************************************************
 * Created by Efendi Hariyadi on 13/06/22, 12:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/06/22, 12:00 PM
 ************************************************/

package com.prateek.nearwe.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*

public  class Utils {

    class CompanionClass {

        companion object {
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