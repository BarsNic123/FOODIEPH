package com.example.foodieph

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val rvFavourites = findViewById<RecyclerView>(R.id.rvFavourites)
        val tvEmpty = findViewById<TextView>(R.id.tvEmptyFavourites)

        btnBack.setOnClickListener { finish() }

        rvFavourites.layoutManager = LinearLayoutManager(this)

        FavouritesManager.getFavourites { list ->
            runOnUiThread {
                if (list.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    rvFavourites.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rvFavourites.visibility = View.VISIBLE
                    rvFavourites.adapter = FavouritesAdapter(list.toMutableList()) { removed ->
                        Toast.makeText(this, "$removed removed from favourites", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
