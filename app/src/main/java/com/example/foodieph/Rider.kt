package com.example.foodieph

data class Rider(
    val id: String = "",
    val fullName: String = "",
    val contactNumber: String = "",
    val email: String = "",
    val address: String = "",
    val licenseNumber: String = "",
    val plateNumber: String = "",
    val vehicleType: String = "",
    val nbiClearanceNumber: String = "",
    val status: String = "pending", // pending, approved, rejected
    val timestamp: com.google.firebase.Timestamp? = null
)
