package com.example.haffa.repository

import android.util.Log
import com.example.haffa.model.Route
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth

class RouteRepository {
    val db = FirebaseFirestore.getInstance()
    val mAuth = Firebase.auth
    val userProfileRepository = UserProfileRepository()

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

        db.collection("routes").document(uid)
            .collection("userRoutes")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("FirestoreRepository", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val routes = mutableListOf<Route>()
                for (document in snapshot!!) {
                    routes.add(document.toObject(Route::class.java))
                }
                callback(routes) // Calling back with the updated list
            }
    }


    fun getAllByPhone(phone: String, callback: (List<Route>) -> Unit) {

        userProfileRepository.getUserIdByUserPhone(phone) { userId ->
            db.collection("routes").document(userId)
                .collection("userRoutes")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("FirestoreRepository", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    val routes = mutableListOf<Route>()
                    for (document in snapshot!!) {
                        routes.add(document.toObject(Route::class.java))
                    }
                    callback(routes)  // Calling back with the updated list
                }
        }
    }

}