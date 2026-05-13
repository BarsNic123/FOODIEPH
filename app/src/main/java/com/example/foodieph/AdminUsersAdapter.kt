package com.example.foodieph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminUsersAdapter(private val users: List<AdminUserItem>) :
    RecyclerView.Adapter<AdminUsersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvAdminUserName)
        val tvPhone: TextView = view.findViewById(R.id.tvAdminUserPhone)
        val tvUid: TextView = view.findViewById(R.id.tvAdminUserUid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.tvName.text = user.name
        holder.tvPhone.text = user.phone
        holder.tvUid.text = "UID: ${user.uid}"
    }

    override fun getItemCount() = users.size
}
