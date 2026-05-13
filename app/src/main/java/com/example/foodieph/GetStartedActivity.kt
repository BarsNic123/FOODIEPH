package com.example.foodieph

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, PhoneActivity::class.java))
        }

        findViewById<Button>(R.id.btnPartnerWithUs).setOnClickListener {
            startActivity(Intent(this, RestaurantRegisterActivity::class.java))
        }

        findViewById<Button>(R.id.btnRegisterRider).setOnClickListener {
            startActivity(Intent(this, RiderRegisterActivity::class.java))
        }

        findViewById<TextView>(R.id.tvAdminLogin).setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
