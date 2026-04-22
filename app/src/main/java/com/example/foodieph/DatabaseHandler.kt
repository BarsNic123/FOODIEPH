package com.example.foodieph

// This acts as our temporary database
object DatabaseHandler {

    // User Credentials
    var loggedInUser: String = ""
    var loggedInPhone: String = ""

    // Order History Storage
    // We use a list of "Orders", where each order is a list of FoodItems
    private val allOrders = mutableListOf<List<FoodItem>>()

    fun addOrder(items: List<FoodItem>) {
        // We create a fresh copy of the list so it stays even when cart is cleared
        allOrders.add(items.toList())
    }

    fun getAllOrders(): List<List<FoodItem>> {
        return allOrders
    }
}