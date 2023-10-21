package com.example.haffa.repository

import android.util.Log
import com.example.haffa.model.UserProfile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserProfileRepository {
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = Firebase.auth

    fun save(userProfile: UserProfile) {
        val uid = mAuth.currentUser!!.uid
        db.collection("users").document(uid)
            .set(userProfile)
            .addOnSuccessListener {
                Log.d("FirestoreRepository", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error writing document", e)
            }
    }
}