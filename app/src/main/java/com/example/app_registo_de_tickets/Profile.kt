package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.app_registo_de_tickets.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Profile : AppCompatActivity() {

    private lateinit var etEmpProfileName: TextView
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        etEmpProfileName = findViewById(R.id.etEmpProfileName)


        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles")
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid
        user = FirebaseAuth.getInstance()
        if (userId != null) {
            checkExistingName(userId!!)
        }

        binding.logoutButton.setOnClickListener {
            user.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private var isExistingProfile: Boolean = false
    private fun checkExistingName(userId: String) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isExistingProfile = snapshot.exists()

                if (isExistingProfile) {
                    loadUserName(userId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Profile, "Error checking existing profile: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun loadUserName(userId: String) {

        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(ProfileModel::class.java)
                val welcomeMessage = "Welcome, ${userProfile?.empProfileName}!"
                etEmpProfileName.text = welcomeMessage
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun navegarParaHistory(view: View) {
        val intent = Intent(this, History::class.java)

        startActivity(intent)
    }
    fun navegarParaUsers(view: View) {
        val intent = Intent(this, History::class.java)

        startActivity(intent)
    }
    fun navegarParaData(view: View) {
        val intent = Intent(this, EditProfile::class.java)

        startActivity(intent)
    }
}