package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfile : AppCompatActivity() {
    private lateinit var etEmpProfileName: EditText
    private lateinit var etEmpLocalidade: EditText
    private lateinit var etEmpContacto: EditText
    private lateinit var btnSaveData: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_profile)

        etEmpProfileName = findViewById(R.id.etEmpProfileName)
        etEmpLocalidade = findViewById(R.id.etEmpLocalidade)
        etEmpContacto = findViewById(R.id.etEmpContacto)
        btnSaveData = findViewById(R.id.btnSave)

        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles")
        auth = FirebaseAuth.getInstance()

        btnSaveData.setOnClickListener {
            val empProfileName = etEmpProfileName.text.toString()
            val empLocalidade = etEmpLocalidade.text.toString()
            val empContacto = etEmpContacto.text.toString()

            if (empProfileName.isEmpty() || empLocalidade.isEmpty() || empContacto.isEmpty()) {
                // Handle empty fields appropriately
                return@setOnClickListener
            }

            val uId = auth.currentUser?.uid
            if (uId != null) {
                // Create a new profile with UID
                val profile = ProfileModel(uId, empProfileName, empLocalidade, empContacto)

                // Save the profile with the user's UID
                databaseReference.child(uId).setValue(profile)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                        etEmpProfileName.text.clear()
                        etEmpLocalidade.text.clear()
                        etEmpContacto.text.clear()
                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_LONG).show()
            }
        }
    }
}
