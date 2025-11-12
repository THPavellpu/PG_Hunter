package com.example.pghunter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PGResponse(
    val message: String,
    val data: List<PGItem>
)
@Parcelize
data class PGItem(
    val _id: String,
    val id_room: Int,
    val images: List<String>,
    val securityAmount: Int,
    val area: String,
    val location: String,
    val rent: Int,
    val seater: String,
    val gender: String,
    val isFeatured: Boolean,
    val soldOut: Boolean,
    val amenities: Amenities,
    val whatsIncluded: WhatsIncluded,
    val houseRules: HouseRules,
    val floor: Int,
    val roomNo: Int,
    val distanceFromAuto: Int,
    val distanceFromCollege: Int,
    val electricityPerUnit: Int,
    val isCoupleFriendly: Boolean,
    val isInternationalFriendly: Boolean,
    val isPetFriendly: Boolean,
    val description: String,
    val note: String?,
    val isPublished: Boolean,
    val listingDate: String,
    val listedBy: String,
    val score: Double = 0.0,
    val score_description: String = "",
    val suggested_pgs: List<Int>? = null
) : Parcelable
@Parcelize
data class PGSuggestion(
    val id_room: Int,
    val images: List<String>,
    val rent: Int,
    val location: String,
    val gender: String,
    val isFeatured: Boolean
) : Parcelable
@Parcelize
data class Amenities(
    val WiFi: Boolean,
    val AC: Boolean,
    val Locker: Boolean,
    val Cctv: Boolean,
    val Inverter: Boolean,
    val Kitchen: Boolean,
    val Parking: Boolean,
    val Balcony: Boolean,
    val Furnished: Boolean,
    val TV: Boolean,
    val FireSafety: Boolean
) : Parcelable

@Parcelize
data class WhatsIncluded(
    val bedAndMattress: Boolean,
    val personalWardrobe: Boolean,
    val studyTable: Boolean,
    val cleaningService: Boolean,
    val geyser: Boolean,
    val highSpeedWifi: Boolean,
    val roPurifiedWater: Boolean,
    val cctv24x7: Boolean,
    val powerBackup: Boolean
) : Parcelable

@Parcelize
data class HouseRules(
    val gateOpenTime: String?,
    val gateCloseTime: String?,
    val visitorAllowed: Boolean,
    val smokingAllowed: Boolean,
    val coupleFriendly: Boolean
) : Parcelable
