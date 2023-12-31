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

    fun getAllProfiles(callback: (List<UserProfile>) -> Unit) {
        db.collection("users")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("FirestoreRepository", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val profiles = mutableListOf<UserProfile>()
                for (document in snapshot!!) {
                    if (document.id != mAuth.currentUser!!.uid)
                        profiles.add(document.toObject(UserProfile::class.java))
                }
                callback(profiles) // Calling back with the updated list
            }
    }

    fun getUserIdByUserPhone(phone: String, callback: (String) -> Unit) {
        db.collection("users")
            .whereEqualTo("telephone", phone)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    callback(document.id)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreRepository", "Error getting documents.", exception)
            }
    }
}