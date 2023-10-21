package com.example.haffa.service

import android.net.Uri
import com.example.haffa.repository.ImageRepository

class ImageService {
    private val imageRepository = ImageRepository()

    fun upload(uri: Uri, onSuccess: (Uri?) -> Unit) {
        imageRepository.upload(uri, onSuccess)
    }
}