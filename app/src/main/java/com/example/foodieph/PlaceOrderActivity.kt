package com.example.foodieph

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlaceOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)

        val rvItems = findViewById<RecyclerView>(R.id.rvCheckoutItems)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val tvGrandTotal = findViewById<TextView>(R.id.tvGrandTotal)
        val tvOrderSerial = findViewById<TextView>(R.id.tvOrderSerial)
        val tvOrderDate = findViewById<TextView>(R.id.tvOrderDate)
        val rgPaymentMethod = findViewById<RadioGroup>(R.id.rgPaymentMethod)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmOrder)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = FoodAdapter(CartManager.getItems())

        // Generate Order ID
        val randomID = "FPH-${(1000..9999).random()}-${(1000..9999).random()}"
        tvOrderSerial.text = "Order ID: #$randomID"

        // Set Date
        val currentDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
        tvOrderDate.text = "Date: $currentDate"

        // Pricing logic
        val subtotal = CartManager.getTotalPrice()
        val deliveryFee = 50
        val total = subtotal + deliveryFee

        tvSubtotal.text = "₱$subtotal.00"
        tvGrandTotal.text = "₱$total.00"

        btnConfirm.setOnClickListener {
            val selectedId = rgPaymentMethod.checkedRadioButtonId
            val method = if (selectedId == R.id.rbCOD) "Cash on Delivery" else "Bank Transfer/G-Cash"

            val itemsToSave = CartManager.getItems()
            if (itemsToSave.isNotEmpty()) {
                // Save to your database handlers
                DatabaseHandler.addOrder(itemsToSave)
                CartManager.addOrderToHistory(itemsToSave)
                SessionManager.saveOrder(itemsToSave)
            }

            Toast.makeText(this, "Order #$randomID confirmed via $method!", Toast.LENGTH_LONG).show()

            CartManager.clearCart()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        btnConfirm.setOnClickListener {
            val selectedId = rgPaymentMethod.checkedRadioButtonId
            val method =
                if (selectedId == R.id.rbCOD) "Cash on Delivery" else "Bank Transfer/G-Cash"

            val itemsToSave = CartManager.getItems()
            if (itemsToSave.isNotEmpty()) {
                // 1. Save to our temporary database
                DatabaseHandler.addOrder(itemsToSave)

                // 2. Also save to the existing history tracker if you're using both
                CartManager.addOrderToHistory(itemsToSave)
            }

            Toast.makeText(this, "Order #$randomID confirmed!", Toast.LENGTH_LONG).show()

            CartManager.clearCart()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}