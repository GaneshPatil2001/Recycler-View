package com.gprogram.recyclerviewdemo.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.gprogram.recyclerviewdemo.R
import com.gprogram.recyclerviewdemo.helper.DatabaseHelper

class AddUserActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etAge: EditText
    lateinit var etContact: EditText
    lateinit var etAddress:EditText
    lateinit var btnBack: Button
    lateinit var btnSave: Button

    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user)

        etName= findViewById(R.id.edtName)
        etAddress=findViewById(R.id.edtAddress)
        etAge=findViewById(R.id.edtAge)
        etContact=findViewById(R.id.edtContact)

        dbHelper= DatabaseHelper(this)
        btnSave=findViewById(R.id.btnSaveAndClose)

        btnSave.setOnClickListener{
                if(checkValidation())
                {
                    val result:Long = dbHelper.insertUser(etName.text.toString(),Integer.parseInt(etAge.text.toString()),etAddress.text.toString(),etContact.text.toString())
                    if(result>0) {
                        Toast.makeText(this, "User Added Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AddUserActivity,InformationActivity::class.java))
                        finish()
                    }
                    else
                        Toast.makeText(this,"Something is wrong",Toast.LENGTH_SHORT).show()
                }
        }

    }
    private fun checkValidation():Boolean
    {
        if(etName.text.toString().isEmpty() ||
            etAge.text.toString().isEmpty() ||
            etAddress.text.toString().isEmpty() ||
            etContact.text.toString().isEmpty() )
            return false
        return true
    }
}