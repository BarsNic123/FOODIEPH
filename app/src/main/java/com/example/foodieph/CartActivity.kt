package com.example.foodieph

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        // Find the total views
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val tvTotalAmount = findViewById<TextView>(R.id.tvTotalAmount)

        rvCartItems.layoutManager = GridLayoutManager(this, 3)
        val itemsInCart = CartManager.getItems()
        rvCartItems.adapter = FoodAdapter(itemsInCart)

        // Calculate Prices
        val subtotal = CartManager.getTotalPrice()
        val deliveryFee = 49
        val grandTotal = subtotal + deliveryFee

        // Update the screen
        tvSubtotal.text = "₱$subtotal.00"
        tvTotalAmount.text = "₱$grandTotal.00"

        btnBack.setOnClickListener { finish() }

        btnCheckout.setOnClickListener {
            if (itemsInCart.isNotEmpty()) {
                startActivity(Intent(this, PlaceOrderActivity::class.java))
            } else {
                Toast.makeText(this, "Empty cart!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}