package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class EditProfile : AppCompatActivity() {
    private lateinit var etEmpProfileName: EditText
    private lateinit var etEmpLocalidade: EditText
    private lateinit var etEmpContacto: EditText
    private lateinit var btnSaveData: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_profile)

        etEmpProfileName = findViewById(R.id.etEmpProfileName)
        etEmpLocalidade = findViewById(R.id.etEmpLocalidade)
        etEmpContacto = findViewById(R.id.etEmpContacto)
        btnSaveData = findViewById(R.id.btnSave)

        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles")
        auth = FirebaseAuth.getInstance()

        // Retrieve current user's UID
        userId = auth.currentUser?.uid

        // Load existing profile data if user has a profile
        if (userId != null) {
            checkExistingProfile(userId!!)
        }

        btnSaveData.setOnClickListener {
            val empProfileName = etEmpProfileName.text.toString()
            val empLocalidade = etEmpLocalidade.text.toString()
            val empContacto = etEmpContacto.text.toString()

            if (empProfileName.isEmpty() || empLocalidade.isEmpty() || empContacto.isEmpty()) {
                // Handle empty fields appropriately
                return@setOnClickListener
            }

            if (userId != null) {
                // Check if the user has an existing profile
                if (isExistingProfile) {
                    // Update the existing profile
                    updateProfile(userId!!, empProfileName, empLocalidade, empContacto)
                } else {
                    // Create a new profile with UID
                    createNewProfile(userId!!, empProfileName, empLocalidade, empContacto)
                }
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_LONG).show()
            }
        }
    }

    private var isExistingProfile: Boolean = false

    private fun checkExistingProfile(userId: String) {
        // Check if the user has an existing profile
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isExistingProfile = snapshot.exists()

                if (isExistingProfile) {
                    // If the profile exists, load the existing data
                    loadUserProfile(userId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Toast.makeText(this@EditProfile, "Error checking existing profile: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadUserProfile(userId: String) {
        // Retrieve the profile for the current user
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Handle the retrieved profile data
                val userProfile = snapshot.getValue(ProfileModel::class.java)
                if (userProfile != null) {
                    // Populate UI elements with existing profile data
                    etEmpProfileName.setText(userProfile.empProfileName)
                    etEmpLocalidade.setText(userProfile.empLocalidade)
                    etEmpContacto.setText(userProfile.empContacto)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Toast.makeText(this@EditProfile, "Error loading profile: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProfile(userId: String, empProfileName: String, empLocalidade: String, empContacto: String) {
        // Update the existing profile with new data
        val profile = ProfileModel(userId, empProfileName, empLocalidade, empContacto)

        databaseReference.child(userId).setValue(profile)
            .addOnCompleteListener {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG).show()
                finish() // Finish the activity after updating the profile
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun createNewProfile(userId: String, empProfileName: String, empLocalidade: String, empContacto: String) {
        // Create a new profile with UID
        val profile = ProfileModel(userId, empProfileName, empLocalidade, empContacto)

        // Save the new profile with the user's UID
        databaseReference.child(userId).setValue(profile)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                finish() // Finish the activity after creating the new profile
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
    private fun navigateToProfilePage() {
        // Intent to start the ProfileActivity
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        finish() // Finish the current activity if you don't want to come back to it
    }
}