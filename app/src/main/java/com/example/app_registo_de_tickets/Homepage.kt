package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

      /*  if (savedInstanceState == null) {
            val fragment = PrimeiroFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }*/

    }
    fun navegarParaHistory(view: View) {
        val intent = Intent(this, History::class.java)

        startActivity(intent)
    }
    fun navegarParaPerfil(view: View) {
        val intent = Intent(this, Profile::class.java)

        startActivity(intent)
    }
}

