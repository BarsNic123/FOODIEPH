package com.example.foodieph

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val etName = findViewById<EditText>(R.id.etSignUpName)
        val etPhone = findViewById<EditText>(R.id.etSignUpPhone)
        val etPassword = findViewById<EditText>(R.id.etSignUpPassword)
        val etConfirm = findViewById<EditText>(R.id.etSignUpConfirmPassword)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val pass = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            if (name.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                etConfirm.error = "Passwords must match"
                return@setOnClickListener
            }

            // SAVE EVERYTHING (Now including the Name!)
            val sharedPref = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("USER_NAME", name)      // Added this line to fix the Profile issue
                putString("USER_PHONE", phone)
                putString("USER_PASSWORD", pass)
                apply()
            }

            Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()

            // Move to the next screen
            startActivity(Intent(this, PhoneActivity::class.java))
            finish()
        }
    }
}