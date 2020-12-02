package com.arifikhsan.githubfavorite.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.arifikhsan.githubfavorite.R
import com.bumptech.glide.Glide.init

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
            if (key == ALARM) {
                isAlarmActive.switchTextOn = sharedPreferences?.getBoolean(ALARM, false).toString()
            }
        }
    }
}