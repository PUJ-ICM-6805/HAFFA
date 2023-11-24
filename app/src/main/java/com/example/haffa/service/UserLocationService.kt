package com.example.haffa.service

import com.example.haffa.model.CustomLocation
import com.example.haffa.repository.UserLocationRepository

class UserLocationService {
    val userLocationRepository = UserLocationRepository()

    fun save(location: CustomLocation) {
        userLocationRepository.save(location)
    }

    fun getByPhone(phone: String, callback: (CustomLocation) -> Unit) {
        userLocationRepository.getByPhone(phone, callback)
    }

}