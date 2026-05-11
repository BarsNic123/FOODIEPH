package com.example.foodieph

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AdminRidersActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tvAdminListTitle).text = "Rider Applications"

        val rv = findViewById<RecyclerView>(R.id.rvAdminList)
        rv.layoutManager = LinearLayoutManager(this)

        db.collection("riders").orderBy("timestamp").get()
            .addOnSuccessListener { snapshot ->
                val items = snapshot.documents.map { doc ->
                    AdminListItem(
                        id = doc.id,
                        title = doc.getString("fullName") ?: "Unknown",
                        subtitle = doc.getString("contactNumber") ?: "",
                        detail = "License: ${doc.getString("licenseNumber") ?: "-"}  |  Plate: ${doc.getString("plateNumber") ?: "-"}",
                        extra = "Vehicle: ${doc.getString("vehicleType") ?: "-"}  |  NBI: ${doc.getString("nbiClearanceNumber") ?: "-"}",
                        status = doc.getString("status") ?: "pending"
                    )
                }
                rv.adapter = AdminListAdapter(items) { id, action ->
                    db.collection("riders").document(id)
                        .update("status", action)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Rider $action", Toast.LENGTH_SHORT).show()
                            recreate()
                        }
                }
            }
    }
}
