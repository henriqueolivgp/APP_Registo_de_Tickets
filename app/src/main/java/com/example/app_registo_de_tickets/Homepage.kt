package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Homepage : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        btnInsertData= findViewById(R.id.btnInsertData)

        btnInsertData.setOnClickListener{
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }
    }
    fun navegarParaPerfil(view: View) {
        val intent = Intent(this, Profile::class.java)

        startActivity(intent)
    }
}

