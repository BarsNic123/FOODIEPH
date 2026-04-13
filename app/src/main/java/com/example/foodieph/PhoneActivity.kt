package com.example.foodieph

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
            val phone = etPhone.text.toString().trim()

            if (phone.isNotEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
            }
        }


        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}