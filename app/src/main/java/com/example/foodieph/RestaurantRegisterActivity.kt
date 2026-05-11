package com.example.foodieph

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantRegisterActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_register)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val etBusinessName = findViewById<EditText>(R.id.etBusinessName)
        val etOwnerName = findViewById<EditText>(R.id.etOwnerName)
        val etContact = findViewById<EditText>(R.id.etRestaurantContact)
        val etEmail = findViewById<EditText>(R.id.etRestaurantEmail)
        val etAddress = findViewById<EditText>(R.id.etRestaurantAddress)
        val etBir = findViewById<EditText>(R.id.etBirCertNumber)
        val etTin = findViewById<EditText>(R.id.etTin)
        val etDti = findViewById<EditText>(R.id.etDtiSecNumber)
        val etPermit = findViewById<EditText>(R.id.etBusinessPermit)
        val etCuisine = findViewById<EditText>(R.id.etCuisineType)
        val etHours = findViewById<EditText>(R.id.etOperatingHours)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitRestaurant)

        btnSubmit.setOnClickListener {
            val businessName = etBusinessName.text.toString().trim()
            val ownerName = etOwnerName.text.toString().trim()
            val contact = etContact.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val bir = etBir.text.toString().trim()
            val tin = etTin.text.toString().trim()
            val dti = etDti.text.toString().trim()
            val permit = etPermit.text.toString().trim()
            val cuisine = etCuisine.text.toString().trim()
            val hours = etHours.text.toString().trim()

            if (businessName.isEmpty() || ownerName.isEmpty() || contact.isEmpty() ||
                email.isEmpty() || address.isEmpty() || bir.isEmpty() || tin.isEmpty() ||
                dti.isEmpty() || permit.isEmpty() || cuisine.isEmpty() || hours.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val restaurant = hashMapOf(
                "businessName" to businessName,
                "ownerName" to ownerName,
                "contactNumber" to contact,
                "email" to email,
                "address" to address,
                "birCertNumber" to bir,
                "tin" to tin,
                "dtiSecNumber" to dti,
                "businessPermitNumber" to permit,
                "cuisineType" to cuisine,
                "operatingHours" to hours,
                "status" to "pending",
                "timestamp" to Timestamp.now()
            )

            db.collection("restaurants").add(restaurant)
                .addOnSuccessListener {
                    Toast.makeText(this,
                        "Application submitted! We'll review your registration within 3-5 business days.",
                        Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Submission failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}
