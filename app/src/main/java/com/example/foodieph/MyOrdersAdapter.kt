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
        val foodImage: ImageView = view.findViewById(R.id.ivOrderFoodImage)
        val foodName: TextView = view.findViewById(R.id.tvOrderFoodName)
        val restaurant: TextView = view.findViewById(R.id.tvOrderRestaurant)
        val quantity: TextView = view.findViewById(R.id.tvOrderQty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_card, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.foodName.text = order.foodName
        holder.restaurant.text = order.restaurant
        holder.foodImage.setImageResource(order.imageResId)
        holder.quantity.text = "Qty: ${order.quantity}"

    }

    override fun getItemCount(): Int = orders.size
}