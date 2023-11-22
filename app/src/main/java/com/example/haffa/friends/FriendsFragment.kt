package com.example.haffa.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.haffa.R
import com.example.haffa.service.UserProfileService

class FriendsFragment : Fragment() {

    private lateinit var adapter: FriendAdapter
    private var userProfileService = UserProfileService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_friends, container, false)

        val contactList = view.findViewById<ListView>(R.id.cardView)
        userProfileService.getAllProfiles { profiles ->
            adapter = FriendAdapter(requireContext(), profiles)
            contactList.adapter = adapter
        }

        return view
    }

}