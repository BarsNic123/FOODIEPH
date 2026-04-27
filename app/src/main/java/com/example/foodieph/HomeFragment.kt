package com.example.foodieph

import android.content.Intent // ADD THIS IMPORT
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.homepage) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val rvCategories = view.findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories.layoutManager = GridLayoutManager(requireContext(), 4)

        rvCategories.adapter = CategoryAdapter(categoryList) { category ->

            val intent = Intent(requireContext(), RestaurantListActivity::class.java)
            intent.putExtra("CATEGORY_NAME", category.name)
            startActivity(intent)
        }
    }
}