package com.example.foodieph

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind IDs from your fragment_profile.xml
        val tvName = view.findViewById<TextView>(R.id.tvUserName)
        val tvPhone = view.findViewById<TextView>(R.id.tvUserPhone)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Load data from SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val name = sharedPref.getString("USER_NAME", "Guest User")
        val phone = sharedPref.getString("USER_PHONE", "09XXXXXXXXX")

        tvName.text = name
        tvPhone.text = phone

        btnLogout.setOnClickListener {
            // Clear session and return to start
            sharedPref.edit().clear().apply()
            val intent = Intent(requireContext(), GetStartedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}