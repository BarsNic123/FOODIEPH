package com.example.foodieph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderGroupAdapter(private val allTransactions: List<List<OrderItem>>) :
    RecyclerView.Adapter<OrderGroupAdapter.GroupViewHolder>() {

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvOrderId: TextView = view.findViewById(R.id.tvGroupOrderId)
        val tvTotal: TextView = view.findViewById(R.id.tvGroupTotal)
        val rvInner: RecyclerView = view.findViewById(R.id.rvInnerItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val singleOrder = allTransactions[position]
        val firstItem = singleOrder[0] // Get details from the first item

        holder.tvOrderId.text = "Order ID: #${firstItem.serialNumber}"

        // Set the credentials at the bottom of the card
        holder.itemView.findViewById<TextView>(R.id.tvGroupLocation).text = "Location: ${firstItem.storeLocation}"
        holder.itemView.findViewById<TextView>(R.id.tvGroupRider).text = "Rider: ${firstItem.riderName} (${firstItem.riderId})"
        holder.itemView.findViewById<TextView>(R.id.tvGroupArrival).text = "Est. Arrival: ${firstItem.deliveryTime}"

        // Calculate Total
        val totalAmount = singleOrder.sumOf { it.totalPrice } + 50
        holder.tvTotal.text = "₱$totalAmount.00"

        // Set up the inner list (which is now just simple food items)
        holder.rvInner.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvInner.adapter = MyOrdersAdapter(singleOrder)

        holder.itemView.setOnClickListener {
            holder.rvInner.visibility = if (holder.rvInner.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int = allTransactions.size
}