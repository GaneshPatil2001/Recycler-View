package com.gprogram.recyclerviewdemo.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gprogram.recyclerviewdemo.R
import com.gprogram.recyclerviewdemo.helper.DatabaseHelper

class updateUserActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etContact: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        etName = findViewById(R.id.edt_UpdateName)
        etAddress = findViewById(R.id.edt_UpdateAddress)
        etAge = findViewById(R.id.edt_UpdateAge)
        etContact = findViewById(R.id.edt_UpdateContact)
        btnUpdate = findViewById(R.id.button_update_user)
        dbHelper = DatabaseHelper(this)

        val db= dbHelper.readableDatabase
        val name1 :String = intent.getStringExtra("name").toString()
        val cursor=db.rawQuery("SELECT id FROM user WHERE name =?", arrayOf(name1))

        var userId :Int =-1
        if(cursor.moveToFirst())
        {
            userId =cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            Log.d("TAG", "onCreate: $userId")
        }

        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val user = dbHelper.getUserById(userId)
        user?.let {
            etName.setText(it["name"] as String)
            etAge.setText((it["age"] as Int).toString())
            etAddress.setText(it["address"] as String)
            etContact.setText(it["contact"] as String)
        }

        btnUpdate.setOnClickListener {
            val updatedRows = dbHelper.updateUser(
                userId,
                etName.text.toString(),
                etAge.text.toString().toInt(),
                etAddress.text.toString(),
                etContact.text.toString()
            )

            if (updatedRows > 0) {
                Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@updateUserActivity, MainActivity::class.java).apply {
                    putExtra("name", name1)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
