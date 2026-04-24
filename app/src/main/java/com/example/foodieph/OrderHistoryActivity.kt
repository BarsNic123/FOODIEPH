package com.example.foodieph

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        // 1. Set up the back button
        val btnBack = findViewById<ImageView>(R.id.btnBackHistory)
        btnBack.setOnClickListener {
            finish()
        }

        // 2. Create the data list with your new credentials
        val orderList = listOf(
            OrderItem(
                imageResId = R.drawable.pizza,
                serialNumber = "SN-992831",
                foodName = "Pepperoni Feast",
                restaurant = "Pizza Hut",
                storeLocation = "SM City Cebu",
                quantity = 1,
                totalPrice = "450.00",
                deliveryTime = "12:45 PM",
                riderName = "Juan Dela Cruz",
                riderId = "RIDER-001"
            ),
            OrderItem(
                imageResId = R.drawable.burger,
                serialNumber = "SN-882104",
                foodName = "Double Cheeseburger",
                restaurant = "Burger King",
                storeLocation = "Ayala Center",
                quantity = 2,
                totalPrice = "320.00",
                deliveryTime = "1:15 PM",
                riderName = "Maria Santos",
                riderId = "RIDER-042"
            )
        )

        // 3. Initialize the RecyclerView
        val rvOrderHistory = findViewById<RecyclerView>(R.id.rvOrderHistory)

        // Use LinearLayoutManager for a clean list view
        rvOrderHistory.layoutManager = LinearLayoutManager(this)

        // Set the adapter
        val adapter = MyOrdersAdapter(orderList)
        rvOrderHistory.adapter = adapter
    }
}