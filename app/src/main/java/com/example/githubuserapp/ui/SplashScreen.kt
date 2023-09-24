package com.example.githubuserapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.R
import com.example.githubuserapp.helper.SettingPreferences
import com.example.githubuserapp.helper.dataStore
import com.example.githubuserapp.model.main.MainModelFactory
import com.example.githubuserapp.model.main.SettingViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

         val pref = SettingPreferences.getInstance(application.dataStore)
         val mainViewModel = ViewModelProvider(
             this,
             MainModelFactory(pref)
         )[SettingViewModel::class.java]

         mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
             if (isDarkModeActive) {
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
             } else {
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
             }
         }
    }
}