package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {


    private lateinit var etEmpName: EditText
    private lateinit var etEmpLocalizacao: EditText
    private lateinit var etEmpProblem: EditText
    private lateinit var btnSaveData: Button
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpLocalizacao = findViewById(R.id.etEmpLocalizacao)
        etEmpProblem = findViewById(R.id.etEmpProblem)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Tickets")
        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid


        btnSaveData.setOnClickListener {
            val empName = etEmpName.text.toString()
            val empLocalizacao = etEmpLocalizacao.text.toString()
            val empProblem = etEmpProblem.text.toString()

            if (empLocalizacao.isEmpty() || empProblem.isEmpty() || empName.isEmpty()) {
                return@setOnClickListener
            }
            val empId = dbRef.push().key!!
            if (userId != null) {
                saveTicketsData(userId!!,empId, empName, empLocalizacao,empProblem)
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun saveTicketsData(userId : String, empId : String ,  empName : String, empLocalizacao: String, empProblem : String) {

        val employee = TicketsModel(userId, empId, empName, empLocalizacao, empProblem)

        dbRef.child(userId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
