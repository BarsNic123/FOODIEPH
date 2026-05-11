package com.example.foodieph

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminDashboardActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val tvPendingRestaurants = findViewById<TextView>(R.id.tvPendingRestaurantsCount)
        val tvPendingRiders = findViewById<TextView>(R.id.tvPendingRidersCount)
        val tvTotalOrders = findViewById<TextView>(R.id.tvTotalOrdersCount)
        val tvTotalUsers = findViewById<TextView>(R.id.tvTotalUsersCount)

        val cardRestaurants = findViewById<CardView>(R.id.cardRestaurants)
        val cardRiders = findViewById<CardView>(R.id.cardRiders)
        val cardOrders = findViewById<CardView>(R.id.cardOrders)
        val cardUsers = findViewById<CardView>(R.id.cardUsers)
        val tvAdminLogout = findViewById<TextView>(R.id.tvAdminLogout)

        // Load counts
        db.collection("restaurants").whereEqualTo("status", "pending").get()
            .addOnSuccessListener { tvPendingRestaurants.text = it.size().toString() }

        db.collection("riders").whereEqualTo("status", "pending").get()
            .addOnSuccessListener { tvPendingRiders.text = it.size().toString() }

        db.collectionGroup("orders").get()
            .addOnSuccessListener { tvTotalOrders.text = it.size().toString() }

        db.collection("users").get()
            .addOnSuccessListener { tvTotalUsers.text = it.size().toString() }

        cardRestaurants.setOnClickListener {
            startActivity(Intent(this, AdminRestaurantsActivity::class.java))
        }

        cardRiders.setOnClickListener {
            startActivity(Intent(this, AdminRidersActivity::class.java))
        }

        cardOrders.setOnClickListener {
            startActivity(Intent(this, AdminOrdersActivity::class.java))
        }

        cardUsers.setOnClickListener {
            startActivity(Intent(this, AdminUsersActivity::class.java))
        }

        tvAdminLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, GetStartedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
