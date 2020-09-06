package com.example.splashpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

class MainActivity : AppCompatActivity() {
    val privateOvserver = PrivacyObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        privateOvserver.addObserver(this, R.layout.activity_splash)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        privateOvserver.onUserLeaveHint()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        privateOvserver.onUserInteraction()
    }

    override fun onResume() {
        super.onResume()
        privateOvserver.onResume()
    }
}
