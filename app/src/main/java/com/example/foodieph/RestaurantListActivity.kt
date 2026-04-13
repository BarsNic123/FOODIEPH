package com.example.foodieph

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RestaurantListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvToolbarTitle = findViewById<TextView>(R.id.tvToolbarTitle)

        val categoryTitle = intent.getStringExtra("CATEGORY_NAME")
        tvToolbarTitle.text = categoryTitle
        btnBack.setOnClickListener {
            finish()
        }
    }
}