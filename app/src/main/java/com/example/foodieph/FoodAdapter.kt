package com.example.foodieph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val foodList: List<FoodItem>) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    // Define the views inside your item_food_choice.xml
    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivFoodImage: ImageView = view.findViewById(R.id.ivFoodImage)
        val tvFoodName: TextView = view.findViewById(R.id.tvFoodName)
        val tvRestaurantName: TextView = view.findViewById(R.id.tvRestaurantName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // This inflates the individual item layout you created earlier
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_choice, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = foodList[position]
        holder.tvFoodName.text = item.name
        holder.tvPrice.text = item.price
        holder.ivFoodImage.setImageResource(item.imageRes)

        holder.itemView.setOnClickListener {
            CartManager.addItem(item)
            android.widget.Toast.makeText(holder.itemView.context, "${item.name} added to cart!", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}