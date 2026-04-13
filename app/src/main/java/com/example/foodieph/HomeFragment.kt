package com.example.foodieph

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.homepage) { // Links to your existing homepage.xml

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Move your category list here
        val categoryList = listOf(
            Category("Pizza", R.drawable.pizza),
            Category("Burger", R.drawable.burger),
            Category("Meal", R.drawable.meal),
            Category("Chinese", R.drawable.chinese),
            Category("Biryani", R.drawable.biriyani),
            Category("Juices", R.drawable.juices),
            Category("Pasta", R.drawable.pasta),
            Category("Dessert", R.drawable.dessert),
            Category("Salad", R.drawable.salad),
            Category("Sandwich", R.drawable.sandwich)
        )

        // Use 'view.findViewById' because we are in a fragment
        val rvCategories = view.findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories.layoutManager = GridLayoutManager(requireContext(), 4)
        rvCategories.adapter = CategoryAdapter(categoryList)
    }
}