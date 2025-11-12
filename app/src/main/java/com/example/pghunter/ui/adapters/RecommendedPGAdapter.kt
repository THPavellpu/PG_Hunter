package com.example.pghunter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pghunter.R
import com.example.pghunter.model.PGItem

class RecommendedPGAdapter(
    private val pgList: List<PGItem>,
    private val onItemClick: (PGItem) -> Unit
) : RecyclerView.Adapter<RecommendedPGAdapter.PGViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PGViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_featured_pg_small, parent, false)
        return PGViewHolder(view)
    }

    override fun onBindViewHolder(holder: PGViewHolder, position: Int) {
        val pg = pgList[position]
        holder.pgName.text = "Room ${pg.roomNo}"
        holder.pgPrice.text = "₹${pg.rent}/month"
        Glide.with(holder.pgImage.context)
            .load(pg.images.firstOrNull())
            .placeholder(R.drawable.sample_room2)
            .into(holder.pgImage)

        holder.itemView.setOnClickListener {
            onItemClick(pg)
        }
    }

    override fun getItemCount(): Int = pgList.size

    class PGViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pgImage: ImageView = itemView.findViewById(R.id.pgImage)
        val pgName: TextView = itemView.findViewById(R.id.pgName)
        val pgPrice: TextView = itemView.findViewById(R.id.pgPrice)
    }
}
