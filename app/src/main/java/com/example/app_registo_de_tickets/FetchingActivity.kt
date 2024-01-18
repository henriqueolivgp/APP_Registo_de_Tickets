package com.example.app_registo_de_tickets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {
    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<TicketsModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<TicketsModel>()

        getTicketsData()
    }

    private fun getTicketsData(){

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE


        dbRef = FirebaseDatabase.getInstance().getReference("Tickets")


        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val ticketsData = empSnap.getValue(TicketsModel::class.java)
                        empList.add(ticketsData!!)
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object: EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@FetchingActivity,TicketsDetailsActivity::class.java)

                            intent.putExtra("empIdUser",empList[position].empIdUser)
                            intent.putExtra("empId",empList[position].empId)
                            intent.putExtra("empName",empList[position].empName)
                            intent.putExtra("empLocalizacao",empList[position].empLocalizacao)
                            intent.putExtra("empProblem",empList[position].empProblem)

                            startActivity(intent)

                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        }
    }
