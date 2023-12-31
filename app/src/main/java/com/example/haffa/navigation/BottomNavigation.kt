package com.example.haffa.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.haffa.R
import com.example.haffa.authentication.LogOut
import com.example.haffa.databinding.ActivityBottomNavigationBinding
import com.example.haffa.friends.FriendsFragment
import com.example.haffa.model.CustomLocation
import com.example.haffa.points.Points
import com.example.haffa.routes.ShowAllRoutesFragment
import com.example.haffa.service.UserLocationService
import com.example.haffa.start.StartRoute
import com.example.haffa.utils.MyLocationService
import com.example.haffa.utils.PermissionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.disposables.Disposable

class BottomNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var myLocationService: MyLocationService
    private var locationDisposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(StartRoute())
        binding.bottomNavigaton.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.startFragment -> replaceFragment(StartRoute())
                R.id.friendsFragment -> replaceFragment(FriendsFragment())
                R.id.routesFragment -> replaceFragment(ShowAllRoutesFragment())
                R.id.pointsFragment -> replaceFragment(Points())
                R.id.logOutFragment -> replaceFragment(LogOut())
                else -> {
                }
            }
            true
        }
        // Initialize Firebase Auth
        mAuth = Firebase.auth

        val myPermissionManager = PermissionManager(this)
        myPermissionManager.requestPermission(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            "It is necessary to access the location"
        ) {
            myLocationService = MyLocationService()
            val userLocationService = UserLocationService()
            myLocationService.getRealTimeLocation(this).subscribe {
                val customLocation = CustomLocation(
                    it.latitude,
                    it.longitude
                )
                userLocationService.save(customLocation)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationDisposable?.dispose()
    }

    override fun onPause() {
        super.onPause()
        locationDisposable?.dispose()
    }

    override fun onResume() {
        super.onResume()
        locationDisposable = myLocationService.getRealTimeLocation(this).subscribe {
            val customLocation = CustomLocation(
                it.latitude,
                it.longitude
            )
            val userLocationService = UserLocationService()
            userLocationService.save(customLocation)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.commit()
    }
}