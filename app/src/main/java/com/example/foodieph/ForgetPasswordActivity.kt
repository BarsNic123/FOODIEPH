package com.example.foodieph

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val etPhone = findViewById<EditText>(R.id.etForgotPhone)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val btnReset = findViewById<Button>(R.id.btnResetPassword)

        btnReset.setOnClickListener {
            val phoneInput = etPhone.text.toString()
            val newPassInput = etNewPassword.text.toString()

            // Link to the "UserDetails" backend/storage
            val sharedPref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)

            val savedPhone = sharedPref.getString("USER_PHONE", "NOT_FOUND")
            Toast.makeText(this, "Stored phone is: $savedPhone", Toast.LENGTH_LONG).show()

            if (phoneInput.isEmpty() || newPassInput.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (phoneInput == savedPhone) {
                // If phone matches, update the password in the "backend"
                with(sharedPref.edit()) {
                    putString("USER_PASSWORD", newPassInput)
                    apply() // Saves changes asynchronously
                }
                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                finish() // Returns user to LoginActivity
            } else {
                Toast.makeText(this, "Phone number not registered", Toast.LENGTH_SHORT).show()
            }
        }
    }
}