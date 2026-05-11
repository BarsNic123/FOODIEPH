package com.example.foodieph

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminOrdersActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_orders)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        val rv = findViewById<RecyclerView>(R.id.rvAdminOrders)
        rv.layoutManager = LinearLayoutManager(this)

        // Fetch all orders across all users via collectionGroup
        db.collectionGroup("orders")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val orders = snapshot.documents.mapNotNull { doc ->
                    val serial = doc.getString("serialNumber") ?: return@mapNotNull null
                    val items = doc.get("items") as? List<Map<String, Any>> ?: emptyList()
                    val total = items.sumOf { (it["totalPrice"] as? Long)?.toInt() ?: 0 } + 49
                    AdminOrderSummary(
                        orderId = serial,
                        itemCount = items.size,
                        totalAmount = total,
                        userId = doc.reference.parent.parent?.id ?: "unknown"
                    )
                }
                rv.adapter = AdminOrdersAdapter(orders)
            }
    }
}
