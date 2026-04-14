package com.example.foodieph

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val rvCartItems = findViewById<RecyclerView>(R.id.rvCartItems)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        // ADD THIS LINE - It links your Kotlin variable to the XML button
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        rvCartItems.layoutManager = GridLayoutManager(this, 3)
        val itemsInCart = CartManager.getItems()
        rvCartItems.adapter = FoodAdapter(itemsInCart)

        // Now this block won't be red anymore!
        btnCheckout.setOnClickListener {
            if (CartManager.getItems().isNotEmpty()) {
                val intent = Intent(this, PlaceOrderActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}