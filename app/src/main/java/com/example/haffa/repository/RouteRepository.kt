package com.example.haffa.repository

import android.util.Log
import com.example.haffa.model.Route
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RouteRepository {
    val db = FirebaseFirestore.getInstance()
    val mAuth = Firebase.auth

    fun save(route: Route) {
        val uid = mAuth.currentUser!!.uid

        db.collection("routes").document(uid)
            .collection("userRoutes")
            .add(route)
            .addOnSuccessListener {
                Log.d("FirestoreRepository", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreRepository", "Error writing document", e)
            }
    }


    fun getAll(callback: (List<Route>) -> Unit) {
        val uid = mAuth.currentUser!!.uid
        val routes = mutableListOf<Route>()

        db.collection("routes").document(uid)
            .collection("userRoutes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    routes.add(document.toObject(Route::class.java))
                }
                callback(routes)  // Calling back with the populated list
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreRepository", "Error getting documents.", exception)
            }
    }

}