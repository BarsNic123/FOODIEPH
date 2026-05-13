package com.example.foodieph

object CartManager {
    private val cartItems = mutableListOf<FoodItem>()
    // Change this to OrderItem to match your Adapter
    private val orderHistory = mutableListOf<OrderItem>()

    fun addOrderToHistory(items: List<OrderItem>) {
        orderHistory.addAll(items)
    }

    fun getOrderHistory(): List<OrderItem> {
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
            it.price.replace("₱", "").replace(",", "").trim().toInt()
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}