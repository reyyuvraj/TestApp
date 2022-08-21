package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.utils.showToast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ntButton = findViewById<Button>(R.id.nt)
        val toastButton = findViewById<Button>(R.id.toast)
        val nhButton = findViewById<Button>(R.id.nh)

        //to view the Notification Log
        //open another app's activity from an action
        ntButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.setClassName(
                "com.android.settings",
                "com.android.settings.Settings\$NotificationStationActivity"
            )
            startActivity(intent)
            finish()
        }

        toastButton.setOnClickListener {
            showToast(this, "Right from utils module")
        }

        nhButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.setClassName(
                "com.android.settings",
                "com.android.settings.notification.history.NotificationHistoryActivity"
            )
            startActivity(intent)
            finish()
        }
    }
}