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

        holder.tvOrderId.text = "Order ID: #${singleOrder[0].serialNumber}"

        // Calculate the total for this specific group
        val totalAmount = singleOrder.sumOf { it.totalPrice } + 50 // Adding delivery fee
        holder.tvTotal.text = "₱$totalAmount.00"

        // Setup Inner List
        holder.rvInner.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvInner.adapter = MyOrdersAdapter(singleOrder)

        // CLICK LOGIC: Show/Hide the items when the card is clicked
        holder.itemView.setOnClickListener {
            if (holder.rvInner.visibility == View.GONE) {
                holder.rvInner.visibility = View.VISIBLE
            } else {
                holder.rvInner.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = allTransactions.size
}