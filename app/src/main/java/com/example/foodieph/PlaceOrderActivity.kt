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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlaceOrderActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

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

        val randomID = "FPH-${(1000..9999).random()}-${(1000..9999).random()}"
        tvOrderSerial.text = "Order ID: #$randomID"

        val currentDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
        tvOrderDate.text = "Date: $currentDate"

        val subtotal = CartManager.getTotalPrice()
        val deliveryFee = 49
        val total = subtotal + deliveryFee
        tvSubtotal.text = "₱$subtotal.00"
        tvGrandTotal.text = "₱$total.00"

        btnBack.setOnClickListener { finish() }

        btnConfirm.setOnClickListener {
            val selectedId = rgPaymentMethod.checkedRadioButtonId
            val method = if (selectedId == R.id.rbCOD) "Cash on Delivery" else "Bank Transfer/G-Cash"

            val currentCartItems = CartManager.getItems()

            if (currentCartItems.isNotEmpty()) {
                val itemsToSave = currentCartItems.map { food ->
                    OrderItem(
                        foodName = food.name,
                        restaurant = food.restaurant,
                        imageResId = food.imageRes,
                        quantity = 1,
                        storeLocation = "Liloan, Cebu",
                        serialNumber = randomID,
                        riderName = "Juan Dela Cruz",
                        riderId = "RIDER-001",
                        deliveryTime = "30-45 mins",
                        totalPrice = food.price.replace("₱", "").replace(",", "").trim().toInt()
                    )
                }

                DatabaseHandler.addOrder(itemsToSave)
                CartManager.addOrderToHistory(itemsToSave)

                val uid = auth.currentUser?.uid
                if (uid != null) {
                    val itemsPayload = itemsToSave.map { item ->
                        hashMapOf(
                            "foodName" to item.foodName,
                            "restaurant" to item.restaurant,
                            "quantity" to item.quantity.toLong(),
                            "totalPrice" to item.totalPrice.toLong(),
                            "serialNumber" to item.serialNumber
                        )
                    }
                    val orderPayload = hashMapOf(
                        "serialNumber" to randomID,
                        "timestamp" to Timestamp.now(),
                        "paymentMethod" to method,
                        "items" to itemsPayload
                    )
                    db.collection("users").document(uid).collection("orders").add(orderPayload)

                    db.collection("users").document(uid)
                        .collection("notifications")
                        .add(
                            hashMapOf(
                                "title" to "Order Confirmed!",
                                "message" to "Order #$randomID via $method is being prepared. Estimated delivery: 30-45 mins.",
                                "timestamp" to Timestamp.now()
                            )
                        )
                }
            }

            Toast.makeText(this, "Order #$randomID confirmed via $method!", Toast.LENGTH_LONG).show()
            CartManager.clearCart()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
