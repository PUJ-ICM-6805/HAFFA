package com.example.haffa.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.haffa.R
import com.example.haffa.databinding.ActivityBottomNavigationBinding
import com.example.haffa.friends.Friends
import com.example.haffa.points.Points
import com.example.haffa.routes.MyRoutes
import com.example.haffa.start.StartRoute

class BottomNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(StartRoute())
        binding.bottomNavigaton.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.startFragment -> replaceFragment(StartRoute())
                R.id.friendsFragment -> replaceFragment(Friends())
                R.id.routesFragment -> replaceFragment(MyRoutes())
                R.id.pointsFragment -> replaceFragment(Points())

                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.commit()
    }
}