package com.example.haffa.repository

import android.util.Log
import com.example.haffa.model.CustomLocation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserLocationRepository {
    val db = FirebaseFirestore.getInstance()
    val mAuth = Firebase.auth
    val userProfileRepository = UserProfileRepository()

    fun save(location: CustomLocation) {
        val uid = mAuth.currentUser!!.uid

        db.collection("locations").document(uid)
            .set(location)
            .addOnSuccessListener {
                Log.d("FirestoreRepository", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error writing document", e)
            }
    }


    fun getByPhone(phone: String, callback: (CustomLocation) -> Unit) {
        userProfileRepository.getUserIdByUserPhone(phone) { userId ->
            db.collection("locations")
                .document(userId)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("FirestoreRepository", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    val location = snapshot!!.toObject(CustomLocation::class.java)
                    if (location != null) {
                        callback(location)
                    }
                }
        }

    }
}