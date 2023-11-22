package com.example.haffa.service

import com.example.haffa.model.UserProfile
import com.example.haffa.repository.UserProfileRepository

class UserProfileService {
    private val userProfileRepository = UserProfileRepository()

    fun save(userProfile: UserProfile) {
        userProfileRepository.save(userProfile)
    }

    fun getAllProfiles(callback: (List<UserProfile>) -> Unit) {
        userProfileRepository.getAllProfiles(callback)
    }
}