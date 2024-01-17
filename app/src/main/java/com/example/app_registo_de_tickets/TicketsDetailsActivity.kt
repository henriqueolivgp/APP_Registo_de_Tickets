package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class TicketsDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpLocalizacao: TextView
    private lateinit var tvEmpProblem: TextView
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }
    }
    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpLocalizacao = findViewById(R.id.tvEmpLocalizacao)
        tvEmpProblem = findViewById(R.id.tvEmpProblem)

        btnUpdate = findViewById(R.id.btnUpdate)
    }
    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpLocalizacao.text = intent.getStringExtra("empLocalizacao")
        tvEmpProblem.text = intent.getStringExtra("empProblem")

    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater=layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpLocalizacao = mDialogView.findViewById<EditText>(R.id.etEmpLocalizacao)
        val etEmpProblem = mDialogView.findViewById<EditText>(R.id.etEmpProblem)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)


        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpLocalizacao.setText(intent.getStringExtra("empLocalizacao").toString())
        etEmpProblem.setText(intent.getStringExtra("empProblem").toString())

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpLocalizacao.text.toString(),
                etEmpProblem.text.toString()
            )
            Toast.makeText(applicationContext,"Dados do Ticket atualizados",Toast.LENGTH_LONG).show()
            //dar update as textviews
            tvEmpName.text = etEmpName.text.toString()
            tvEmpLocalizacao.text = etEmpLocalizacao.text.toString()
            tvEmpProblem.text =  etEmpProblem.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateEmpData(
        id:String,
        name:String,
        localizacao: String,
        problem:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Tickets").child(id)
        val empInfo = TicketsModel(id,name,localizacao,problem)
        dbRef.setValue(empInfo)
    }
}
