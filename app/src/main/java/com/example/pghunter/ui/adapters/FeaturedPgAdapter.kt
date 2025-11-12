package com.example.pghunter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pghunter.R
import com.example.pghunter.model.Amenities
import com.example.pghunter.model.PGItem

class FeaturedPgAdapter(
    private var pgList: MutableList<PGItem>,
    private val onItemClick: (PGItem) -> Unit
) : RecyclerView.Adapter<FeaturedPgAdapter.PgViewHolder>() {

    inner class PgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pgImage: ImageView = itemView.findViewById(R.id.pgImage)
        val pgName: TextView = itemView.findViewById(R.id.pgName)
        val pgLocation: TextView = itemView.findViewById(R.id.pgLocation)
        val pgPrice: TextView = itemView.findViewById(R.id.pgPrice)
        val pgDistance: TextView = itemView.findViewById(R.id.pgDistance)
        val amenitiesIconsLayout: LinearLayout = itemView.findViewById(R.id.amenitiesIconsLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PgViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_featured_pg, parent, false)
        return PgViewHolder(view)
    }

    override fun onBindViewHolder(holder: PgViewHolder, position: Int) {
        val pg = pgList[position]

        holder.pgName.text = "Room ${pg.id_room}"
        holder.pgLocation.text = pg.location
        holder.pgPrice.text = "₹${pg.rent}/month"

        val distanceInKm = pg.distanceFromCollege / 1000.0
        holder.pgDistance.text = String.format("%.1f KM from LPU", distanceInKm)

        if (pg.images.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(pg.images[0])
                .placeholder(R.drawable.sample_room2)
                .into(holder.pgImage)
        } else {
            holder.pgImage.setImageResource(R.drawable.sample_room2)
        }

        // Add amenity icons
        addAmenityIcons(holder.amenitiesIconsLayout, pg.amenities)

        holder.itemView.setOnClickListener { onItemClick(pg) }
    }

    override fun getItemCount(): Int = pgList.size

    fun updateList(newList: List<PGItem>) {
        pgList.clear()
        pgList.addAll(newList)
        notifyDataSetChanged()
    }

    private fun addAmenityIcons(layout: LinearLayout, amenities: Amenities) {
        layout.removeAllViews()

        val keyAmenities = listOf(
            Pair(amenities.WiFi, R.drawable.outline_android_wifi_3_bar_24),
            Pair(amenities.AC, R.drawable.outline_ac_unit_24),
            Pair(amenities.Kitchen, R.drawable.outline_skillet_cooktop_24),
            Pair(amenities.Furnished, R.drawable.outline_chair_24)
        )

        for ((isAvailable, iconRes) in keyAmenities) {
            if (isAvailable) {
                val icon = ImageView(layout.context).apply {
                    setImageResource(iconRes)
                    layoutParams = LinearLayout.LayoutParams(60, 60).apply {
                        marginEnd = 16
                    }
                }
                layout.addView(icon)
            }
        }
    }
}
