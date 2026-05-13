package com.example.foodieph

data class Restaurant(
    val id: String = "",
    val businessName: String = "",
    val ownerName: String = "",
    val contactNumber: String = "",
    val email: String = "",
    val address: String = "",
    val birCertNumber: String = "",
    val tin: String = "",
    val dtiSecNumber: String = "",
    val businessPermitNumber: String = "",
    val cuisineType: String = "",
    val operatingHours: String = "",
    val status: String = "pending", // pending, approved, rejected
    val timestamp: com.google.firebase.Timestamp? = null
)
