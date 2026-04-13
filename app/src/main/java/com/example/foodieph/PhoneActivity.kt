package com.example.foodieph

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PhoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_phone)

        val etPhone = findViewById<EditText>(R.id.etLoginPhone)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)

        btnNext.setOnClickListener {
            val phoneInput = etPhone.text.toString().trim()
            val sharedPref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val savedPhone = sharedPref.getString("USER_PHONE", "")

            if (phoneInput == savedPhone && phoneInput.isNotEmpty()) {
                // Pass the phone number to LoginActivity so the user doesn't type it again
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("PHONE_NUMBER", phoneInput)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Phone number not found", Toast.LENGTH_SHORT).show()
            }
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}