package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class History : AppCompatActivity() {
    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<TicketsModel>
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        dbRef = FirebaseDatabase.getInstance().getReference("Tickets")
        auth = FirebaseAuth.getInstance()

        userId = auth.currentUser?.uid

        empList = arrayListOf<TicketsModel>()
        if (userId != null) {
                getTicketsData()
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTicketsData() {
        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        val ticketsReference = dbRef.orderByChild("empId").equalTo(userId)

        ticketsReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()

                for (empSnap in snapshot.children) {
                    val ticketsData = empSnap.getValue(TicketsModel::class.java)
                    if (ticketsData != null) {
                        empList.add(ticketsData)
                    }
                }


                val mAdapter = EmpAdapter(empList)
                empRecyclerView.adapter = mAdapter

                mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@History, HistoryDetailsDetails::class.java)

                        intent.putExtra("empName", empList[position].empName)
                        intent.putExtra("empLocalizacao", empList[position].empLocalizacao)
                        intent.putExtra("empProblem", empList[position].empProblem)

                        startActivity(intent)
                    }
                })

                empRecyclerView.visibility = View.VISIBLE
                tvLoadingData.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                empRecyclerView.visibility = View.GONE
                tvLoadingData.visibility = View.VISIBLE
                tvLoadingData.text = "Error loading data"
            }
        })

    }
}