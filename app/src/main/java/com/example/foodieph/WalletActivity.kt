package com.example.foodieph

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class WalletActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvBalance = findViewById<TextView>(R.id.tvWalletBalance)
        val etTopUp = findViewById<EditText>(R.id.etTopUpAmount)
        val btnTopUp = findViewById<Button>(R.id.btnTopUp)
        val rvHistory = findViewById<RecyclerView>(R.id.rvWalletHistory)

        btnBack.setOnClickListener { finish() }
        rvHistory.layoutManager = LinearLayoutManager(this)

        val uid = auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "Please log in again to use your wallet.", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val walletRef = db.collection("users").document(uid).collection("wallet").document("balance")

        // Load balance
        walletRef.get().addOnSuccessListener { doc ->
            val balance = doc.getLong("amount") ?: 0L
            tvBalance.text = "₱$balance.00"
        }

        // Load transaction history
        db.collection("users").document(uid).collection("wallet")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val transactions = snapshot.documents
                    .filter { it.id != "balance" }
                    .mapNotNull { doc ->
                        val type = doc.getString("type") ?: return@mapNotNull null
                        val amount = doc.getLong("amount") ?: 0L
                        "$type  ₱$amount"
                    }
                rvHistory.adapter = WalletHistoryAdapter(transactions)
            }

        btnTopUp.setOnClickListener {
            val amountStr = etTopUp.text.toString().trim()
            if (amountStr.isEmpty()) {
                Toast.makeText(this, "Enter an amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val amount = amountStr.toLongOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get current balance then add
            walletRef.get().addOnSuccessListener { doc ->
                val current = doc.getLong("amount") ?: 0L
                val newBalance = current + amount
                walletRef.set(hashMapOf("amount" to newBalance))
                    .addOnSuccessListener {
                        tvBalance.text = "₱$newBalance.00"
                        etTopUp.setText("")
                        // Log transaction
                        db.collection("users").document(uid).collection("wallet")
                            .add(hashMapOf(
                                "type" to "Top-up",
                                "amount" to amount,
                                "timestamp" to com.google.firebase.Timestamp.now()
                            ))
                        Toast.makeText(this, "₱$amount added to wallet!", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
