package com.example.foodieph

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminListAdapter(
    private val items: List<AdminListItem>,
    private val onAction: (id: String, action: String) -> Unit
) : RecyclerView.Adapter<AdminListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvAdminItemTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvAdminItemSubtitle)
        val tvDetail: TextView = view.findViewById(R.id.tvAdminItemDetail)
        val tvExtra: TextView = view.findViewById(R.id.tvAdminItemExtra)
        val tvStatus: TextView = view.findViewById(R.id.tvAdminItemStatus)
        val btnApprove: Button = view.findViewById(R.id.btnApprove)
        val btnReject: Button = view.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_application, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvSubtitle.text = item.subtitle
        holder.tvDetail.text = item.detail
        holder.tvExtra.text = item.extra
        holder.tvStatus.text = item.status.uppercase()

        when (item.status) {
            "approved" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"))
                holder.btnApprove.isEnabled = false
                holder.btnApprove.alpha = 0.4f
            }
            "rejected" -> {
                holder.tvStatus.setTextColor(Color.parseColor("#C62828"))
                holder.btnReject.isEnabled = false
                holder.btnReject.alpha = 0.4f
            }
            else -> {
                holder.tvStatus.setTextColor(Color.parseColor("#FF9800"))
            }
        }

        holder.btnApprove.setOnClickListener { onAction(item.id, "approved") }
        holder.btnReject.setOnClickListener { onAction(item.id, "rejected") }
    }

    override fun getItemCount() = items.size
}
