package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun navegarParaHistory(view: View) {
        val intent = Intent(this, History::class.java)

        startActivity(intent)
    }
}