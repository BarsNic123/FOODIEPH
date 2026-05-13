package com.example.foodieph

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Maps a phone number to a deterministic pseudo-email for Firebase Email/Password auth.
 *
 * Replace [app/google-services.json] with the real file from Firebase Console for your package
 * `com.example.foodieph`. Enable Email/Password in Authentication.
 *
 * Admin: create an Auth user, then add Firestore document `admins/{uid}` for that user's uid.
 */
object FirebaseUserHelper {

    private const val EMAIL_DOMAIN = "foodieph.app"

    fun phoneToEmail(phone: String): String {
        val digits = phone.filter { it.isDigit() }
        val local = if (digits.isEmpty()) "user" else digits
        return "$local@$EMAIL_DOMAIN"
    }

    fun syncUserProfileToFirestore(name: String, phone: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(uid).set(
            hashMapOf(
                "name" to name,
                "phone" to phone,
                "updatedAt" to Timestamp.now()
            ),
            com.google.firebase.firestore.SetOptions.merge()
        )
    }

    /**
     * After email/password signup or sign-in, ensure the Firestore user profile exists.
     */
    fun ensureFirestoreUser(name: String?, phone: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val data = hashMapOf<String, Any>(
            "phone" to phone,
            "updatedAt" to Timestamp.now()
        )
        if (!name.isNullOrBlank()) data["name"] = name
        db.collection("users").document(uid).set(data, com.google.firebase.firestore.SetOptions.merge())
    }

    /**
     * Creates Firebase user after local signup, or links if collision with same credentials.
     */
    fun createFirebaseAccountAfterSignup(
        phone: String,
        password: String,
        name: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val email = phoneToEmail(phone)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                ensureFirestoreUser(name, phone)
                onSuccess()
            }
            .addOnFailureListener { e ->
                if (e is FirebaseAuthUserCollisionException) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            ensureFirestoreUser(name, phone)
                            onSuccess()
                        }
                        .addOnFailureListener { onFailure(it.message ?: "Could not sign in") }
                } else {
                    onFailure(e.message ?: "Registration failed")
                }
            }
    }

    /**
     * Signs into Firebase with the same pseudo-email used at signup.
     */
    fun signInFirebaseWithPhonePassword(
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val email = phoneToEmail(phone)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { signInErr ->
                val code = (signInErr as? FirebaseAuthException)?.errorCode
                val shouldCreateAccount = code == "ERROR_USER_NOT_FOUND"
                if (shouldCreateAccount) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            ensureFirestoreUser(null, phone)
                            onSuccess()
                        }
                        .addOnFailureListener { createErr ->
                            if (createErr is FirebaseAuthUserCollisionException) {
                                onFailure("Invalid phone or password")
                            } else {
                                onFailure(createErr.message ?: "Login failed")
                            }
                        }
                } else {
                    onFailure("Invalid phone or password")
                }
            }
    }
}
