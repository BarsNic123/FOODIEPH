package com.example.foodieph

object CartManager {
    private val cartItems = mutableListOf<FoodItem>()
    private val orderHistory = mutableListOf<FoodItem>()

    fun addOrderToHistory(items: List<FoodItem>) {
        orderHistory.addAll(items)
    }

    fun getOrderHistory(): List<FoodItem> {
        return orderHistory
    }

    fun addItem(item: FoodItem) {
        cartItems.add(item)
    }

    fun getItems(): List<FoodItem> {
        return cartItems
    }

    fun getTotalPrice(): Int {
        return cartItems.sumOf {
            it.price.replace("₱", "").trim().toInt()
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}