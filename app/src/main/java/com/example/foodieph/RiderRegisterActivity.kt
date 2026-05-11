package com.example.foodieph

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class RiderRegisterActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider_register)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val etFullName = findViewById<EditText>(R.id.etRiderFullName)
        val etContact = findViewById<EditText>(R.id.etRiderContact)
        val etEmail = findViewById<EditText>(R.id.etRiderEmail)
        val etAddress = findViewById<EditText>(R.id.etRiderAddress)
        val etLicense = findViewById<EditText>(R.id.etLicenseNumber)
        val etPlate = findViewById<EditText>(R.id.etPlateNumber)
        val etVehicle = findViewById<EditText>(R.id.etVehicleType)
        val etNbi = findViewById<EditText>(R.id.etNbiClearance)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitRider)

        btnSubmit.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val contact = etContact.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val license = etLicense.text.toString().trim()
            val plate = etPlate.text.toString().trim()
            val vehicle = etVehicle.text.toString().trim()
            val nbi = etNbi.text.toString().trim()

            if (fullName.isEmpty() || contact.isEmpty() || email.isEmpty() ||
                address.isEmpty() || license.isEmpty() || plate.isEmpty() ||
                vehicle.isEmpty() || nbi.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rider = hashMapOf(
                "fullName" to fullName,
                "contactNumber" to contact,
                "email" to email,
                "address" to address,
                "licenseNumber" to license,
                "plateNumber" to plate,
                "vehicleType" to vehicle,
                "nbiClearanceNumber" to nbi,
                "status" to "pending",
                "timestamp" to Timestamp.now()
            )

            db.collection("riders").add(rider)
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
