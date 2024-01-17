package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpLocalizacao: EditText
    private lateinit var etEmpProblem: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpLocalizacao = findViewById(R.id.etEmpLocalizacao)
        etEmpProblem = findViewById(R.id.etEmpProblem)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Tickets")

        btnSaveData.setOnClickListener {
            saveTicketsData()
        }


    }

    private fun saveTicketsData() {

        //getting values
        val empName = etEmpName.text.toString()
        val empLocalizacao = etEmpLocalizacao.text.toString()
        val empProblem = etEmpProblem.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Introduza o nome da empresa"
        }
        if (empLocalizacao.isEmpty()) {
            etEmpLocalizacao.error = "Introduza a localizacao da empresa"
        }
        if (empProblem.isEmpty()) {
            etEmpProblem.error = "Introduza o problema"
        }

        val empId = dbRef.push().key!!

        val employee = TicketsModel(empId, empName, empLocalizacao, empProblem)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpLocalizacao.text.clear()
                etEmpProblem.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
