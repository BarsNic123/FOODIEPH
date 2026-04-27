package com.example.foodieph

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val etPhone = findViewById<EditText>(R.id.etLoginPhone)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvSignUpLink = findViewById<TextView>(R.id.tvSignUpLink)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // Automatically fill the phone number passed from PhoneActivity
        val passedPhone = intent.getStringExtra("PHONE_NUMBER")
        etPhone.setText(passedPhone)

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        tvSignUpLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val phoneInput = etPhone.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim() // Added trim() for safety

            // Access the "backend" storage
            val sharedPref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)

            // Fetch the LATEST credentials from the file
            val savedPhone = sharedPref.getString("USER_PHONE", "")
            val savedPassword = sharedPref.getString("USER_PASSWORD", "")

            if (phoneInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verify both Phone AND Password against the backend
            if (phoneInput == savedPhone && passwordInput == savedPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {

                Toast.makeText(this, "Invalid phone or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}