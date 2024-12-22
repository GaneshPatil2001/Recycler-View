package com.gprogram.recyclerviewdemo.adaptor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gprogram.recyclerviewdemo.R
import com.gprogram.recyclerviewdemo.UI.MainActivity
import com.gprogram.recyclerviewdemo.entities.Information
import com.gprogram.recyclerviewdemo.UI.InformationActivity

class InformationAdaptor(
    private val informationList: List<Information>,
    private val activity: InformationActivity
) : RecyclerView.Adapter<InformationAdaptor.InformationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.information_item_row_layout, parent, false)
        return InformationViewHolder(view)
    }

    override fun getItemCount(): Int = informationList.size

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.bind(informationList[position], activity)
    }

    class InformationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtName: TextView = itemView.findViewById(R.id.txtPersonName)
        private val cardView: CardView = itemView.findViewById(R.id.crdUserName)

        fun bind(information: Information, activity: InformationActivity) {
            txtName.text = information.Name
            cardView.setOnClickListener {
                Log.d("TAG", "bind: ${information.Name}")
                val intent = Intent(activity, MainActivity::class.java).apply {

                    putExtra("name", information.Name)
                    putExtra("age", information.Age)
                    putExtra("address", information.Address)
                    putExtra("Contact", information.Contact)
                }
                activity.startActivity(intent)
                activity.finish()
                Log.d("TAG", "bind: ${information.Address}")
            }
        }
    }
}
