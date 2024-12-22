package com.gprogram.recyclerviewdemo.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gprogram.recyclerviewdemo.R
import com.gprogram.recyclerviewdemo.helper.DatabaseHelper

class userFragment : Fragment() {

    private var userName: String? = null
    private var userAge: String? = null
    private var userAddress: String? = null
    private var userContact: String? = null
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(requireContext())
        arguments?.let {
            userName = it.getString(ARG_NAME)
            userAge = it.getString(ARG_AGE)
            userAddress = it.getString(ARG_ADDRESS)
            userContact = it.getString(ARG_CONTACT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        view.findViewById<TextView>(R.id.txtName).text = userName
        view.findViewById<TextView>(R.id.txtAge).text = userAge
        view.findViewById<TextView>(R.id.txtAddress).text = userAddress
        view.findViewById<TextView>(R.id.txtContact).text = userContact

        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val intent = Intent(requireContext(), updateUserActivity::class.java).apply {
                putExtra("name", userName)
            }
            startActivity(intent)
            requireActivity().finish()
        }
        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            val intent = Intent(requireContext(),InformationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        view.findViewById<Button>(R.id.btnDelete).setOnClickListener {

            val db= dbHelper.readableDatabase

            val cursor=db.rawQuery("SELECT id FROM user WHERE name =?", arrayOf(userName))

            var userId :Int =-1
            if(cursor.moveToFirst())
            {
                userId =cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                Log.d("TAG", "onCreate: $userId")
            }
            dbHelper.deleteUser(userId)
            val intent = Intent(requireContext(),InformationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return view

    }
    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_AGE = "age"
        private const val ARG_ADDRESS = "address"
        private const val ARG_CONTACT = "contact"

        fun newInstance(name: String, age: String, address: String, contact: String) =
            userFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_AGE, age)
                    putString(ARG_ADDRESS, address)
                    putString(ARG_CONTACT, contact)
                }
            }
    }
}
