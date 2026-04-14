package com.example.foodieph

object CartManager {
    private val cartItems = mutableListOf<FoodItem>()

    fun addItem(item: FoodItem) {
        cartItems.add(item)
    }

    fun getItems(): List<FoodItem> {
        return cartItems
    }

    fun getTotalPrice(): Int {
        // This removes the '₱' symbol and converts the price to an integer to sum it up
        return cartItems.sumOf { it.price.replace("₱", "").toInt() }
    }

    fun clearCart() {
        cartItems.clear()
    }
}