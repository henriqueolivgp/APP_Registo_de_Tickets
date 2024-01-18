package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HistoryDetails : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpLocalizacao: TextView
    private lateinit var tvEmpProblem: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_details)

        initView()
        setValuesToViews()
    }
    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpLocalizacao = findViewById(R.id.tvEmpLocalizacao)
        tvEmpProblem = findViewById(R.id.tvEmpProblem)
    }
    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpLocalizacao.text = intent.getStringExtra("empLocalizacao")
        tvEmpProblem.text = intent.getStringExtra("empProblem")

    }
}