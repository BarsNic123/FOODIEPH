package com.example.foodieph

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlaceOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)

        val rvItems = findViewById<RecyclerView>(R.id.rvCheckoutItems)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val tvGrandTotal = findViewById<TextView>(R.id.tvGrandTotal)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmOrder)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        // 1. Show items in a vertical list for checkout
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = FoodAdapter(CartManager.getItems())

        // 2. Calculate Fees
        val subtotal = CartManager.getTotalPrice()
        val deliveryFee = 49
        val total = subtotal + deliveryFee

        tvSubtotal.text = "₱$subtotal.00"
        tvGrandTotal.text = "₱$total.00"

        // 3. Confirm Order
        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Order Confirmed! Delivering soon.", Toast.LENGTH_LONG).show()
            CartManager.clearCart()
            // Go back to the very start of the app (MainActivity)
            val intent = android.content.Intent(this, MainActivity::class.java)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        btnBack.setOnClickListener { finish() }
    }
}