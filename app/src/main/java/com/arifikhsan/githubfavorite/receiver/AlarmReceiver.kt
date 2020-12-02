package com.arifikhsan.githubfavorite.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"

        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101

        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"
    }


    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        TODO("AlarmReceiver.onReceive() is not implemented")
    }
}