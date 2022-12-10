package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ChatsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}