package com.example.foodieph

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotificationsActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val rvNotifications = findViewById<RecyclerView>(R.id.rvNotifications)
        val tvEmpty = findViewById<TextView>(R.id.tvEmptyNotifications)

        btnBack.setOnClickListener { finish() }
        rvNotifications.layoutManager = LinearLayoutManager(this)

        val uid = auth.currentUser?.uid
        if (uid == null) {
            tvEmpty.text = "Sign in to see notifications."
            tvEmpty.visibility = View.VISIBLE
            rvNotifications.visibility = View.GONE
            return
        }

        db.collection("users").document(uid)
            .collection("notifications")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val notifs = snapshot.documents.mapNotNull { doc ->
                    val title = doc.getString("title") ?: return@mapNotNull null
                    val message = doc.getString("message") ?: ""
                    NotificationItem(title, message)
                }
                if (notifs.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    rvNotifications.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rvNotifications.visibility = View.VISIBLE
                    rvNotifications.adapter = NotificationsAdapter(notifs)
                }
            }
    }
}
