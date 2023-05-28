package com.example.testapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.white)
        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}