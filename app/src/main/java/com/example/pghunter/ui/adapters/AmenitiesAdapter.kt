package com.example.pghunter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pghunter.R

class AmenitiesAdapter(private val amenities: List<String>) :
    RecyclerView.Adapter<AmenitiesAdapter.AmenityViewHolder>() {

    inner class AmenityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amenityIcon: ImageView = itemView.findViewById(R.id.amenityIcon)
        val amenityName: TextView = itemView.findViewById(R.id.amenityName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_amenity, parent, false)
        return AmenityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AmenityViewHolder, position: Int) {
        val amenity = amenities[position]
        holder.amenityName.text = amenity
        holder.amenityIcon.setImageResource(getIconForAmenity(amenity))
    }

    override fun getItemCount(): Int = amenities.size

    private fun getIconForAmenity(amenity: String): Int {
        // TODO: Replace with your actual drawable resources
        return when (amenity.lowercase()) {
            "wifi" -> R.drawable.outline_android_wifi_3_bar_24
            "ac" -> R.drawable.outline_ac_unit_24
            "food" -> R.drawable.outline_food_bank_24
            "laundry" -> R.drawable.outline_laundry_24
            "locker" -> R.drawable.outline_mobile_lock_portrait_24
            "cctv" -> R.drawable.outline_speed_camera_24
            "inverter" -> R.drawable.outline_hot_tub_24
            "kitchen" -> R.drawable.outline_skillet_cooktop_24
            "parking" -> R.drawable.outline_bike_dock_24
            "balcony" -> R.drawable.outline_chair_24
            "furnished" -> R.drawable.outline_chair_24
            "tv" -> R.drawable.outline_tv_24
            "firesafety" -> R.drawable.outline_fire_extinguisher_24
            else -> R.drawable.ic_launcher_background
        }
    }
}
