package com.example.haffa.friends

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.haffa.R
import com.example.haffa.databinding.CardFriendBinding
import com.example.haffa.model.UserProfile
import com.example.haffa.routes.ShowAllRoutesFragment

class FriendAdapter(
    private val context: Context,
    private val userProfiles: List<UserProfile>
) : BaseAdapter() {

    override fun getCount(): Int = userProfiles.size

    override fun getItem(position: Int): Any = userProfiles[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding: CardFriendBinding = if (convertView == null) {
            CardFriendBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            CardFriendBinding.bind(convertView)
        }

        val userProfile = getItem(position) as UserProfile
        binding.name.text = userProfile.fullName
        binding.phone.text = userProfile.telephone
        binding.user.text = userProfile.username

        binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("PHONE", userProfile.telephone)
            bundle.putSerializable("FRIEND_NAME", userProfile.username)
            val fragmentTransaction = (binding.root.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            val newFragment = ShowAllRoutesFragment()
            newFragment.arguments = bundle
            fragmentTransaction.replace(R.id.frame_container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
            true
        }

        return binding.root
    }
}
