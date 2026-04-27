package com.example.foodieph

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestaurantListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvToolbarTitle = findViewById<TextView>(R.id.tvToolbarTitle)
        val rvRestaurants = findViewById<RecyclerView>(R.id.rvRestaurants)

        val categoryTitle = intent.getStringExtra("CATEGORY_NAME") ?: "Food Items"
        tvToolbarTitle.text = categoryTitle

        btnBack.setOnClickListener {
            finish()
        }

        val allFoods = getAllFoodItems()
        val filteredList = allFoods.filter { it.category == categoryTitle }

        rvRestaurants.layoutManager = GridLayoutManager(this, 3)
        rvRestaurants.adapter = FoodAdapter(filteredList)
    }

    private fun getAllFoodItems(): List<FoodItem> {
        return listOf(
            // PIZZA
            FoodItem("Pepperoni", "Pizza Hut", "₱399", "4.8", R.drawable.pizza, "Pizza"),
            FoodItem("Hawaiian", "Greenwich", "₱349", "4.5", R.drawable.pizza, "Pizza"),
            FoodItem("Cheese", "S&R Pizza", "₱499", "4.7", R.drawable.pizza, "Pizza"),
            FoodItem("Manager's", "Shakey's", "₱550", "4.9", R.drawable.pizza, "Pizza"),
            FoodItem("Veggie", "Domino's", "₱320", "4.3", R.drawable.pizza, "Pizza"),

            // BURGER
            FoodItem("Yumburger", "Jollibee", "₱40", "4.5", R.drawable.burger, "Burger"),
            FoodItem("Whopper", "Burger King", "₱189", "4.7", R.drawable.burger, "Burger"),
            FoodItem("Zinger", "KFC", "₱160", "4.6", R.drawable.burger, "Burger"),
            FoodItem("Big Mac", "McDonald's", "₱175", "4.4", R.drawable.burger, "Burger"),
            FoodItem("Cheeseburger", "Army Navy", "₱220", "4.8", R.drawable.burger, "Burger"),

            // JUICES
            FoodItem("Orange Juice", "Fruitas", "₱85", "4.5", R.drawable.juices, "Juices"),
            FoodItem("Mango Shake", "Thirsty", "₱95", "4.7", R.drawable.juices, "Juices"),
            FoodItem("Lemonade", "Lemon Co", "₱75", "4.3", R.drawable.juices, "Juices"),
            FoodItem("Apple Juice", "Healthy To Go", "₱90", "4.4", R.drawable.juices, "Juices"),
            FoodItem("Buko Juice", "Local Stall", "₱50", "4.8", R.drawable.juices, "Juices"),

            // PASTA
            FoodItem("Spaghetti", "Jollibee", "₱60", "4.5", R.drawable.pasta, "Pasta"),
            FoodItem("Carbonara", "Greenwich", "₱120", "4.4", R.drawable.pasta, "Pasta"),
            FoodItem("Lasagna", "Pizza Hut", "₱150", "4.7", R.drawable.pasta, "Pasta"),
            FoodItem("Baked Mac", "Red Ribbon", "₱110", "4.3", R.drawable.pasta, "Pasta"),
            FoodItem("Pesto", "Italianni's", "₱280", "4.6", R.drawable.pasta, "Pasta"),

            // DESSERT
            FoodItem("Halo-Halo", "Chowking", "₱85", "4.7", R.drawable.dessert, "Dessert"),
            FoodItem("Leche Flan", "Local Sweets", "₱50", "4.8", R.drawable.dessert, "Dessert"),
            FoodItem("Chocolate Cake", "Red Ribbon", "₱450", "4.6", R.drawable.dessert, "Dessert"),
            FoodItem("Mango Float", "Homemade", "₱120", "4.9", R.drawable.dessert, "Dessert"),
            FoodItem("Ice Cream", "Selecta", "₱35", "4.5", R.drawable.dessert, "Dessert"),

            // SALAD
            FoodItem("Caesar Salad", "Shakey's", "₱220", "4.4", R.drawable.salad, "Salad"),
            FoodItem("Fruit Salad", "Kusina", "₱150", "4.5", R.drawable.salad, "Salad"),
            FoodItem("Garden Salad", "Healthy Eats", "₱180", "4.2", R.drawable.salad, "Salad"),
            FoodItem("Potato Salad", "Classic Savory", "₱130", "4.3", R.drawable.salad, "Salad"),
            FoodItem("Macaroni", "Lady's Choice", "₱140", "4.6", R.drawable.salad, "Salad"),

            // SANDWICH
            FoodItem("Clubhouse", "Cafe", "₱150", "4.5", R.drawable.sandwich, "Sandwich"),
            FoodItem("Ham & Cheese", "Bakery", "₱45", "4.2", R.drawable.sandwich, "Sandwich"),
            FoodItem("Tuna Melt", "Subway", "₱160", "4.4", R.drawable.sandwich, "Sandwich"),
            FoodItem("Egg Sandwich", "Breakfast Co", "₱65", "4.1", R.drawable.sandwich, "Sandwich"),
            FoodItem("Chicken Spread", "Pan de Manila", "₱55", "4.6", R.drawable.sandwich, "Sandwich")

        )
    }
}