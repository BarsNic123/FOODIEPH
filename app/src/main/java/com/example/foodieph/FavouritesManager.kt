package com.example.foodieph

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FavouritesManager {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun addFavourite(restaurantName: String, onComplete: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onComplete(false)
            return
        }
        db.collection("users").document(uid)
            .collection("favourites")
            .document(restaurantName)
            .set(hashMapOf("name" to restaurantName, "timestamp" to com.google.firebase.Timestamp.now()))
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun removeFavourite(restaurantName: String, onComplete: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onComplete(false)
            return
        }
        db.collection("users").document(uid)
            .collection("favourites")
            .document(restaurantName)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun getFavourites(onResult: (List<String>) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(emptyList())
            return
        }
        db.collection("users").document(uid)
            .collection("favourites")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { it.getString("name") }
                onResult(list)
            }
    }

    fun isFavourite(restaurantName: String, onResult: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false)
            return
        }
        db.collection("users").document(uid)
            .collection("favourites")
            .document(restaurantName)
            .get()
            .addOnSuccessListener { onResult(it.exists()) }
    }
}
