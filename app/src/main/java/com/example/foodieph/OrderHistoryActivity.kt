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

        val btnBack = findViewById<ImageView>(R.id.btnBackHistory)
        btnBack.setOnClickListener { finish() }

        // 1. Get the grouped orders (List of Lists) from DatabaseHandler
        val groupedOrders = DatabaseHandler.getAllOrders()

        val rvOrderHistory = findViewById<RecyclerView>(R.id.rvOrderHistory)
        rvOrderHistory.layoutManager = LinearLayoutManager(this)

        // 2. Use the OrderGroupAdapter to show each order as its own "tab"
        // This adapter handles the inner list of food items automatically
        val adapter = OrderGroupAdapter(groupedOrders)
        rvOrderHistory.adapter = adapter
    }
}