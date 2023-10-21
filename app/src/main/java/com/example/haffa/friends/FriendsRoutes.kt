package com.example.haffa.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haffa.databinding.FragmentFriendsRoutesBinding

class FriendsRoutes : Fragment() {

    private lateinit var binding: FragmentFriendsRoutesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsRoutesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val friend = arguments?.getSerializable("friend", Friend::class.java)

        //if (friend != null) {
            //showFriendRoutes(friend)
        //}
    }

    private fun showFriendRoutes(friend: Friend) {
        with(binding){
            textView2.text = friend.name

        }
    }

}