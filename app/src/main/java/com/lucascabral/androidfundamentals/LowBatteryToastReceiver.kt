package com.lucascabral.androidfundamentals

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class LowBatteryToastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.i("BROADCAST_RECEIVER", "Your battery is low Toast")
        Toast.makeText(context, "Your battery is low Toast", Toast.LENGTH_LONG).show()
    }
}