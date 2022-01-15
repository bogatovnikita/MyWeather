package com.bogatovnikita.myweather.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bogatovnikita.myweather.R

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, R.string.air_plain_mode, Toast.LENGTH_LONG).show()
    }
}