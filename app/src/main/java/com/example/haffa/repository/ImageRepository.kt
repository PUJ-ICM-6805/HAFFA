package com.example.haffa.repository

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ImageRepository {
    fun upload(uri: Uri, onSuccess: (downloadUri: Uri?) -> Unit) {
        val ref = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    onSuccess(downloadUri)
                }
            }
            .addOnFailureListener {
                Log.w("Upload", "Upload failed", it)
                onSuccess(null)
            }
    }
}