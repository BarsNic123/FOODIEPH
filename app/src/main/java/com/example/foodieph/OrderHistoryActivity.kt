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

        val rvOrderHistory = findViewById<RecyclerView>(R.id.rvOrderHistory)
        val btnBack = findViewById<ImageView>(R.id.btnBackHistory)

        // Set up the RecyclerView
        rvOrderHistory.layoutManager = LinearLayoutManager(this)

        // Retrieve the saved orders from CartManager
        val historyItems = CartManager.getOrderHistory()

        // Reuse your FoodAdapter to show the items
        rvOrderHistory.adapter = FoodAdapter(historyItems)

        btnBack.setOnClickListener {
            finish()
        }
    }
}