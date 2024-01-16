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

        userId = auth.currentUser?.uid

        if (userId != null) {
            checkExistingProfile(userId!!)
        }

        btnSaveData.setOnClickListener {
            val empProfileName = etEmpProfileName.text.toString()
            val empLocalidade = etEmpLocalidade.text.toString()
            val empContacto = etEmpContacto.text.toString()

            if (empProfileName.isEmpty() || empLocalidade.isEmpty() || empContacto.isEmpty()) {
                return@setOnClickListener
            }

            if (userId != null) {
                if (isExistingProfile) {
                    updateProfile(userId!!, empProfileName, empLocalidade, empContacto)
                } else {
                    createNewProfile(userId!!, empProfileName, empLocalidade, empContacto)
                }
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_LONG).show()
            }
        }
    }

    private var isExistingProfile: Boolean = false

    private fun checkExistingProfile(userId: String) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isExistingProfile = snapshot.exists()

                if (isExistingProfile) {
                    btnSaveData.text = "Atualizar Profile"
                    loadUserProfile(userId)
                }else{
                    btnSaveData.text = "Criar Profile"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfile, "Error checking existing profile: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadUserProfile(userId: String) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(ProfileModel::class.java)
                if (userProfile != null) {
                    etEmpProfileName.setText(userProfile.empProfileName)
                    etEmpLocalidade.setText(userProfile.empLocalidade)
                    etEmpContacto.setText(userProfile.empContacto)
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@EditProfile, "Error loading profile: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProfile(userId: String, empProfileName: String, empLocalidade: String, empContacto: String) {

        val profile = ProfileModel(userId, empProfileName, empLocalidade, empContacto)

        databaseReference.child(userId).setValue(profile)
            .addOnCompleteListener {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun createNewProfile(userId: String, empProfileName: String, empLocalidade: String, empContacto: String) {
        val profile = ProfileModel(userId, empProfileName, empLocalidade, empContacto)

        databaseReference.child(userId).setValue(profile)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
    private fun navigateToProfilePage() {

        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
        finish()
    }
}