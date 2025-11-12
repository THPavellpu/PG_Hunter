package com.example.pghunter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pghunter.R

class DetailsAdapter(private val items: List<Pair<Int, String>>) :
    RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.amenityIcon)
        val name: TextView = itemView.findViewById(R.id.amenityName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_amenity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (iconRes, name) = items[position]
        holder.icon.setImageResource(iconRes)
        holder.name.text = name
    }

    override fun getItemCount(): Int = items.size
}
