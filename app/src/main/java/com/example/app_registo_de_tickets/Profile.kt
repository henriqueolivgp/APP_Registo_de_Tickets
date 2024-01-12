package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.app_registo_de_tickets.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()
        binding.logoutButton.setOnClickListener {
            user.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    fun navegarParaHistory(view: View) {
        val intent = Intent(this, History::class.java)

        startActivity(intent)
    }
}