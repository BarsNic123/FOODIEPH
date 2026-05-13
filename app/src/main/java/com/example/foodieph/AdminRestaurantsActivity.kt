package com.example.foodieph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminRestaurantsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tvAdminListTitle).text = "Restaurant Applications"

        val rv = findViewById<RecyclerView>(R.id.rvAdminList)
        rv.layoutManager = LinearLayoutManager(this)

        db.collection("restaurants").orderBy("timestamp").get()
            .addOnSuccessListener { snapshot ->
                val items = snapshot.documents.map { doc ->
                    AdminListItem(
                        id = doc.id,
                        title = doc.getString("businessName") ?: "Unknown",
                        subtitle = doc.getString("ownerName") ?: "",
                        detail = "TIN: ${doc.getString("tin") ?: "-"}  |  BIR: ${doc.getString("birCertNumber") ?: "-"}",
                        extra = "Contact: ${doc.getString("contactNumber") ?: "-"}  |  ${doc.getString("cuisineType") ?: "-"}  |  ${doc.getString("operatingHours") ?: "-"}",
                        status = doc.getString("status") ?: "pending"
                    )
                }
                rv.adapter = AdminListAdapter(items) { id, action ->
                    db.collection("restaurants").document(id)
                        .update("status", action)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Restaurant $action", Toast.LENGTH_SHORT).show()
                            recreate()
                        }
                }
            }
    }
}
