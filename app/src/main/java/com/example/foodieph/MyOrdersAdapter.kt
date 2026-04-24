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
        val foodImage: ImageView = view.findViewById(R.id.ivFoodImage)
        val foodName: TextView = view.findViewById(R.id.tvFoodName)
        val restaurant: TextView = view.findViewById(R.id.tvRestaurant)
        val storeLocation: TextView = view.findViewById(R.id.tvStoreLocation)
        val serialNumber: TextView = view.findViewById(R.id.tvSerialNumber)
        val riderDetails: TextView = view.findViewById(R.id.tvRiderDetails)
        val deliveryTime: TextView = view.findViewById(R.id.tvDeliveryTime)
        val totalPrice: TextView = view.findViewById(R.id.tvTotalPrice)
        val quantity: TextView = view.findViewById(R.id.tvQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        // Basic Info
        holder.foodName.text = order.foodName
        holder.restaurant.text = order.restaurant
        holder.foodImage.setImageResource(order.imageResId)
        holder.quantity.text = "Qty: ${order.quantity}"

        // New Credentials Logic
        holder.storeLocation.text = "Location: ${order.storeLocation}"
        holder.serialNumber.text = "SN: ${order.serialNumber}"
        holder.riderDetails.text = "Rider: ${order.riderName} (ID: ${order.riderId})"
        holder.deliveryTime.text = "Est. Arrival: ${order.deliveryTime}"
        holder.totalPrice.text = "Amount Due: ₱${order.totalPrice}"
    }

    override fun getItemCount(): Int = orders.size
}