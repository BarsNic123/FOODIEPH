package com.example.foodieph

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvName = view.findViewById<TextView>(R.id.tvUserName)
        val tvPhone = view.findViewById<TextView>(R.id.tvUserPhone)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val clMyOrders = view.findViewById<ConstraintLayout>(R.id.clMyOrders)
        val clFavourites = view.findViewById<ConstraintLayout>(R.id.clFavourites)
        val clWallet = view.findViewById<ConstraintLayout>(R.id.clWallet)
        val clNotifications = view.findViewById<ConstraintLayout>(R.id.clNotifications)

        // Load user data from Firestore when signed in with Firebase; otherwise use local profile
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    val sp = requireActivity().getSharedPreferences(
                        "UserDetails",
                        android.content.Context.MODE_PRIVATE
                    )
                    tvName.text = doc.getString("name")
                        ?: sp.getString("USER_NAME", "Guest")
                    tvPhone.text = doc.getString("phone")
                        ?: sp.getString("USER_PHONE", "No phone")
                }
                .addOnFailureListener {
                    applyLocalProfile(tvName, tvPhone)
                }
        } else {
            applyLocalProfile(tvName, tvPhone)
        }

        clMyOrders.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }

        clFavourites.setOnClickListener {
            startActivity(Intent(requireContext(), FavouritesActivity::class.java))
        }

        clWallet.setOnClickListener {
            startActivity(Intent(requireContext(), WalletActivity::class.java))
        }

        clNotifications.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationsActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            // Also clear SharedPreferences for legacy support
            requireActivity()
                .getSharedPreferences("UserDetails", android.content.Context.MODE_PRIVATE)
                .edit().clear().apply()
            val intent = Intent(requireContext(), GetStartedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun applyLocalProfile(tvName: TextView, tvPhone: TextView) {
        val sharedPref = requireActivity()
            .getSharedPreferences("UserDetails", android.content.Context.MODE_PRIVATE)
        tvName.text = sharedPref.getString("USER_NAME", "Guest User")
        tvPhone.text = sharedPref.getString("USER_PHONE", "Not Registered")
    }
}
