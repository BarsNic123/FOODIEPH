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

        val tvName = view.findViewById<TextView>(R.id.tvUserName)
        val tvPhone = view.findViewById<TextView>(R.id.tvUserPhone)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)


        val clMyOrders = view.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.clMyOrders)
        val sharedPref = requireActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val name = sharedPref.getString("USER_NAME", "Guest User")
        val phone = sharedPref.getString("USER_PHONE", "Not Registered")

        tvName.text = name
        tvPhone.text = phone

        clMyOrders.setOnClickListener {
            val intent = Intent(requireContext(), OrderHistoryActivity::class.java)
            startActivity(intent)
        }

        // Logout logic: Clears the "backend" and returns to the start screen
        btnLogout.setOnClickListener {
            sharedPref.edit().clear().apply()
            val intent = Intent(requireContext(), GetStartedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}