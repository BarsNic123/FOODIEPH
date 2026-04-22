package com.example.foodieph

object SessionManager {
    // Store User Info
    var userName: String = ""
    var userPhone: String = ""

    // Store Orders (List of Lists, because one order can have many items)
    private val allOrders = mutableListOf<List<FoodItem>>()

    fun saveOrder(items: List<FoodItem>) {
        // We create a new copy of the list so it doesn't get cleared
        allOrders.add(ArrayList(items))
    }

    fun getAllOrders(): List<List<FoodItem>> {
        return allOrders
    }
}