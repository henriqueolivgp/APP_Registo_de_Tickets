package com.example.app_registo_de_tickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HistoryDetails : AppCompatActivity() {
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
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpLocalizacao = findViewById(R.id.tvEmpLocalizacao)
        tvEmpProblem = findViewById(R.id.tvEmpProblem)
    }
    private fun setValuesToViews() {
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpLocalizacao.text = intent.getStringExtra("empLocalizacao")
        tvEmpProblem.text = intent.getStringExtra("empProblem")

    }
}