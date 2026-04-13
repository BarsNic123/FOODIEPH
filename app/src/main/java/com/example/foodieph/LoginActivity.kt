package com.example.foodieph

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView // Don't forget this import
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val etPhone = findViewById<EditText>(R.id.etLoginPhone)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        // RE-ADD THIS LINE:
        val tvSignUpLink = findViewById<TextView>(R.id.tvSignUpLink)

        val passedPhone = intent.getStringExtra("PHONE_NUMBER")
        etPhone.setText(passedPhone)
        etPhone.isEnabled = false

        // RE-ADD THIS CLICK LISTENER:
        tvSignUpLink.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val passwordInput = etPassword.text.toString()
            val sharedPref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val savedPassword = sharedPref.getString("USER_PASSWORD", "")

            if (passwordInput.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if (passwordInput == savedPassword) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}