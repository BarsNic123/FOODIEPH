package com.example.foodieph

data class OrderItem(
    val imageResId: Int,
    val serialNumber: String,
    val foodName: String,
    val restaurant: String,
    val storeLocation: String,
    val quantity: Int,
    val totalPrice: String,
    val deliveryTime: String,
    val riderName: String,
    val riderId: String
)