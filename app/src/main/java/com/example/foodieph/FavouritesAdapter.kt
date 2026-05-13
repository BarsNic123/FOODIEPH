package com.example.foodieph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavouritesAdapter(
    private val items: MutableList<String>,
    private val onRemove: (String) -> Unit
) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvFavRestaurantName)
        val btnRemove: ImageView = view.findViewById(R.id.btnRemoveFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favourite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = items[position]
        holder.tvName.text = name
        holder.btnRemove.setOnClickListener {
            FavouritesManager.removeFavourite(name) { success ->
                if (success) {
                    val pos = holder.adapterPosition
                    items.removeAt(pos)
                    notifyItemRemoved(pos)
                    onRemove(name)
                }
            }
        }
    }

    override fun getItemCount() = items.size
}
