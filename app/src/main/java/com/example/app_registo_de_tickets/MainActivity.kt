package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        navegarParaLogin()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        // Start the Runnable to navigate after 3 seconds
        handler.postDelayed(runnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        // Remove callbacks to avoid triggering navigation when the activity is not visible
        handler.removeCallbacks(runnable)
    }

    fun navegarParaLogin(view: View? = null) {
        // Cancel the delayed navigation if user clicks on the screen
        handler.removeCallbacks(runnable)

        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to avoid memory leaks
        handler.removeCallbacks(runnable)
    }
}