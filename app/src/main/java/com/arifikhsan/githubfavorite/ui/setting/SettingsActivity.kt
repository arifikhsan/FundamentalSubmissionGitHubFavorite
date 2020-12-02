package com.arifikhsan.githubfavorite.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.arifikhsan.githubfavorite.R
import com.arifikhsan.githubfavorite.receiver.AlarmReceiver

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fr_settings_holder, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {

        private lateinit var ALARM: String
        private lateinit var isAlarmActive: SwitchPreferenceCompat
        private lateinit var alarmReceiver: AlarmReceiver

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.alarm_preference, rootKey)
            init()
            setSummaries()
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        private fun setSummaries() {
            val sharedPreferences = preferenceManager.sharedPreferences
            isAlarmActive.switchTextOn = sharedPreferences.getBoolean(ALARM, false).toString()
        }

        private fun init() {
            ALARM = resources.getString(R.string.key_alarm)
            isAlarmActive = findPreference<SwitchPreferenceCompat>(ALARM) as SwitchPreferenceCompat
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            when (key) {
                ALARM -> {
                    val toggle = sharedPreferences?.getBoolean(ALARM, false)
                    isAlarmActive.switchTextOn = toggle.toString()
                    if (toggle != null) {
                        alarmReceiver = AlarmReceiver()
                        if (toggle) {
                            context?.let {
                                alarmReceiver.setRepeatingAlarm(
                                    it,
                                    "09:00",
                                    "Halo, semoga harimu menyenangkan :)"
                                )
                            }
                        } else {
                            context?.let {
                                alarmReceiver.cancelAlarm(
                                    it,
                                    AlarmReceiver.TYPE_REPEATING
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}