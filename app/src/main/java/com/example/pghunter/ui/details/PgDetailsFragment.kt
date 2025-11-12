package com.example.pghunter.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pghunter.R
import com.example.pghunter.model.Amenities
import com.example.pghunter.model.HouseRules
import com.example.pghunter.model.PGItem
import com.example.pghunter.model.WhatsIncluded
import com.example.pghunter.ui.adapters.DetailsAdapter
import com.example.pghunter.ui.adapters.ImageSliderAdapter
import java.net.URLEncoder

class PgDetailsFragment : Fragment() {

    private lateinit var dotsIndicator: LinearLayout
    private var dots: Array<ImageView?> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pg_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pgItem = arguments?.getParcelable<PGItem>("pg_item")

        val imagesRecyclerView = view.findViewById<RecyclerView>(R.id.imagesRecyclerView)
        dotsIndicator = view.findViewById(R.id.dotsIndicator)

        val pgName = view.findViewById<TextView>(R.id.pgName)
        val pgLocation = view.findViewById<TextView>(R.id.pgLocation)
        val pgPrice = view.findViewById<TextView>(R.id.pgPrice)

        val amenitiesRecyclerView = view.findViewById<RecyclerView>(R.id.amenitiesRecyclerView)
        val whatsIncludedRecyclerView = view.findViewById<RecyclerView>(R.id.whatsIncludedRecyclerView)
        val houseRulesRecyclerView = view.findViewById<RecyclerView>(R.id.houseRulesRecyclerView)

        // ✅ Initialize Price Sentiment Section
        val priceSentimentSection = view.findViewById<View>(R.id.priceSentiment)
        val priceSentimentView = PriceSentimentView(priceSentimentSection)

        pgItem?.let {
            pgName.text = "Room ${it.roomNo}"
            pgLocation.text = it.location
            pgPrice.text = "₹${it.rent}/month"

            // ✅ Set price sentiment (score & description)
            priceSentimentView.setPriceSentiment(it.score.toFloat(), it.score_description)

            // ✅ Image Slider Setup
            imagesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            imagesRecyclerView.adapter = ImageSliderAdapter(it.images)

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(imagesRecyclerView)

            setupDots(it.images.size)

            imagesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (position != RecyclerView.NO_POSITION) {
                        updateDots(position)
                    }
                }
            })

            // ✅ Amenities, What's Included, and House Rules setup
            setupDetailList(amenitiesRecyclerView, getAmenities(it.amenities))
            setupDetailList(whatsIncludedRecyclerView, getWhatsIncluded(it.whatsIncluded))
            setupDetailList(houseRulesRecyclerView, getHouseRules(it.houseRules))
        }

        // ✅ WhatsApp Booking Button
        val bookNowButton = view.findViewById<Button>(R.id.bookNowButton)
        bookNowButton.setOnClickListener {
            val adminNumber = "+917626958976" // Replace with your WhatsApp number
            val message =
                "I'm interested in booking Room ${pgItem?.roomNo} in ${pgItem?.location} for ₹${pgItem?.rent}/month."
            val url =
                "https://wa.me/${adminNumber.replace("+", "")}?text=${URLEncoder.encode(message, "UTF-8")}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    // ✅ Helper for Amenities, House Rules, etc.
    private fun setupDetailList(recyclerView: RecyclerView, items: List<Pair<Int, String>>) {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = DetailsAdapter(items)
    }

    private fun getAmenities(amenities: Amenities): List<Pair<Int, String>> {
        val list = mutableListOf<Pair<Int, String>>()
        if (amenities.WiFi) list.add(Pair(R.drawable.outline_android_wifi_3_bar_24, "WiFi"))
        if (amenities.AC) list.add(Pair(R.drawable.outline_ac_unit_24, "AC"))
        if (amenities.Locker) list.add(Pair(R.drawable.outline_mobile_lock_portrait_24, "Locker"))
        if (amenities.Cctv) list.add(Pair(R.drawable.outline_speed_camera_24, "CCTV"))
        if (amenities.Inverter) list.add(Pair(R.drawable.outline_power_24, "Inverter"))
        if (amenities.Kitchen) list.add(Pair(R.drawable.outline_skillet_cooktop_24, "Kitchen"))
        if (amenities.Parking) list.add(Pair(R.drawable.outline_bike_dock_24, "Parking"))
        if (amenities.Balcony) list.add(Pair(R.drawable.outline_chair_umbrella_24, "Balcony"))
        if (amenities.Furnished) list.add(Pair(R.drawable.outline_chair_24, "Furnished"))
        if (amenities.TV) list.add(Pair(R.drawable.outline_tv_24, "TV"))
        if (amenities.FireSafety) list.add(Pair(R.drawable.outline_fire_extinguisher_24, "Fire Safety"))
        return list
    }

    private fun getWhatsIncluded(whatsIncluded: WhatsIncluded): List<Pair<Int, String>> {
        val list = mutableListOf<Pair<Int, String>>()
        if (whatsIncluded.bedAndMattress) list.add(Pair(R.drawable.outline_bed_24, "Bed & Mattress"))
        if (whatsIncluded.personalWardrobe) list.add(Pair(R.drawable.outline_checkroom_24, "Wardrobe"))
        if (whatsIncluded.studyTable) list.add(Pair(R.drawable.outline_desk_24, "Study Table"))
        if (whatsIncluded.cleaningService) list.add(Pair(R.drawable.outline_cleaning_services_24, "Cleaning"))
        if (whatsIncluded.geyser) list.add(Pair(R.drawable.outline_water_heater_24, "Geyser"))
        if (whatsIncluded.highSpeedWifi) list.add(Pair(R.drawable.outline_wifi_24, "High-Speed WiFi"))
        if (whatsIncluded.roPurifiedWater) list.add(Pair(R.drawable.outline_water_drop_24, "RO Water"))
        if (whatsIncluded.cctv24x7) list.add(Pair(R.drawable.outline_speed_camera_24, "24x7 CCTV"))
        if (whatsIncluded.powerBackup) list.add(Pair(R.drawable.outline_power_24, "Power Backup"))
        return list
    }

    private fun getHouseRules(houseRules: HouseRules): List<Pair<Int, String>> {
        val list = mutableListOf<Pair<Int, String>>()
        if (houseRules.visitorAllowed)
            list.add(Pair(R.drawable.outline_emoji_people_24, "Visitors Allowed"))
        if (houseRules.smokingAllowed)
            list.add(Pair(R.drawable.outline_smoking_rooms_24, "Smoking Allowed"))
        if (houseRules.coupleFriendly)
            list.add(Pair(R.drawable.outline_favorite_24, "Couple Friendly"))
        return list
    }

    // ✅ Dots Indicator for Image Slider
    private fun setupDots(count: Int) {
        dots = arrayOfNulls(count)
        dotsIndicator.removeAllViews()

        for (i in 0 until count) {
            dots[i] = ImageView(requireContext())
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_inactive)
            )

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dotsIndicator.addView(dots[i], params)
        }

        if (dots.isNotEmpty()) {
            dots[0]?.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_active)
            )
        }
    }

    private fun updateDots(position: Int) {
        for (i in dots.indices) {
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_inactive)
            )
        }
        if (position >= 0 && position < dots.size) {
            dots[position]?.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_active)
            )
        }
    }
}
