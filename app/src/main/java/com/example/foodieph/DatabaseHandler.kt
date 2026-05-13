package com.example.foodieph

object DatabaseHandler {

    private val allOrders = mutableListOf<List<OrderItem>>()
    fun addOrder(items: List<OrderItem>) {
        allOrders.add(items.toList())
    }
    fun getAllOrders(): List<List<OrderItem>> {
        return allOrders
    }
}