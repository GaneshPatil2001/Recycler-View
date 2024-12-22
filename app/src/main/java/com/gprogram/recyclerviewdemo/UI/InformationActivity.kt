package com.gprogram.recyclerviewdemo.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gprogram.recyclerviewdemo.R
import com.gprogram.recyclerviewdemo.adaptor.InformationAdaptor
import com.gprogram.recyclerviewdemo.helper.DatabaseHelper
import com.gprogram.recyclerviewdemo.entities.Information

class InformationActivity : AppCompatActivity() {
    private lateinit var rvInformation: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        rvInformation = findViewById(R.id.rvInformation)
        btnAdd = findViewById(R.id.button_add_user)
        dbHelper = DatabaseHelper(this)

        setupRecyclerView()
        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddUserActivity::class.java))
            finish()
        }
    }

    private fun setupRecyclerView() {
        val users = dbHelper.getAllUsers()
        if (users.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            return
        }

        val informationList = users.map {
            Information(
                it["name"] as String,
                (it["age"] as Int).toString(),
                it["address"] as String,
                it["contact"] as String
            )
        }

        rvInformation.apply {
            adapter = InformationAdaptor(informationList, this@InformationActivity)
            layoutManager = LinearLayoutManager(this@InformationActivity)
        }
    }
}
