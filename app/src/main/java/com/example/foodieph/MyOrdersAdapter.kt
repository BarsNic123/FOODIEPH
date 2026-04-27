package com.example.foodieph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyOrdersAdapter(private val orders: List<OrderItem>) :
    RecyclerView.Adapter<MyOrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Updated to match your NEW item_order_card.xml IDs
        val foodImage: ImageView = view.findViewById(R.id.ivOrderFoodImage)
        val foodName: TextView = view.findViewById(R.id.tvOrderFoodName)
        val restaurant: TextView = view.findViewById(R.id.tvOrderRestaurant)
        val quantity: TextView = view.findViewById(R.id.tvOrderQty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Updated to use the correct layout file name
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_card, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        // Only binding the items that still exist in your layout
        holder.foodName.text = order.foodName
        holder.restaurant.text = order.restaurant
        holder.foodImage.setImageResource(order.imageResId)
        holder.quantity.text = "Qty: ${order.quantity}"

        // Removed SN, Rider, Location, and Price lines to match your clean UI
    }

    override fun getItemCount(): Int = orders.size
}