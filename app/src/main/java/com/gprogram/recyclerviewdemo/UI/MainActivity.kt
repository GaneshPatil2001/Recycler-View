package com.gprogram.recyclerviewdemo.UI

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gprogram.recyclerviewdemo.R
import com.gprogram.recyclerviewdemo.helper.DatabaseHelper

class MainActivity : AppCompatActivity() {
    private lateinit var  dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrieve data from the Intent
        val name = intent.getStringExtra("name") ?: ""

        dbHelper= DatabaseHelper(this)
        val db= dbHelper.readableDatabase
        val cursor=db.rawQuery("SELECT id FROM user WHERE name =?", arrayOf(intent.getStringExtra("name")))

        var userId :Int =-1
        if(cursor.moveToFirst())
        {
            userId =cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            Log.d("TAG", "onCreate: $userId")
        }

        var age :Int = -1
        var address :String = ""
        var contact :String =""
        val user = dbHelper.getUserById(userId)
        user?.let {

            age= it["age"] as Int

            address=it["address"] as String
            contact=it["contact"] as String
        }

        val fragment = userFragment.newInstance(name, age.toString(), address, contact)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()


    }
}
