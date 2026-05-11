package com.example.foodieph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminOrdersAdapter(private val orders: List<AdminOrderSummary>) :
    RecyclerView.Adapter<AdminOrdersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderId: TextView = view.findViewById(R.id.tvAdminOrderId)
        val tvItemCount: TextView = view.findViewById(R.id.tvAdminOrderItems)
        val tvTotal: TextView = view.findViewById(R.id.tvAdminOrderTotal)
        val tvUserId: TextView = view.findViewById(R.id.tvAdminOrderUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.tvOrderId.text = "Order #${order.orderId}"
        holder.tvItemCount.text = "${order.itemCount} item(s)"
        holder.tvTotal.text = "₱${order.totalAmount}.00"
        holder.tvUserId.text = "User: ${order.userId}"
    }

    override fun getItemCount() = orders.size
}
