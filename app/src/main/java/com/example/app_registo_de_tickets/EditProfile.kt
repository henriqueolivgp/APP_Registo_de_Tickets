package com.example.app_registo_de_tickets

import android.content.Intent
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
    private lateinit var auth : FirebaseAuth
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_profile)
        auth = FirebaseAuth.getInstance()
        val firebaseUser = auth.currentUser

        btnSaveData = findViewById(R.id.btnSave)

        btnSaveData.setOnClickListener {

                //getting values
                val empProfileName = etEmpProfileName.text.toString()
                val empLocalidade = etEmpLocalidade.text.toString()
                val empContacto = etEmpContacto.text.toString()

            if(firebaseUser != null){

                val uid = firebaseUser.uid

                if (empProfileName.isEmpty()) {
                    etEmpProfileName.error = "Introduza o nome"
                }
                if (empLocalidade.isEmpty()) {
                    etEmpLocalidade.error = "Introduza a localidade"
                }
                if (empContacto.isEmpty()) {
                    etEmpContacto.error = "Introduza o contacto"
                }

                val profile = ProfileModel(empProfileName, empLocalidade, empContacto)

                dbRef = FirebaseDatabase.getInstance().reference

                dbRef.child("Profiles").child(uid).setValue(profile)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                        etEmpProfileName.text.clear()
                        etEmpLocalidade.text.clear()
                        etEmpContacto.text.clear()


                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }

            }

        }
    }


}