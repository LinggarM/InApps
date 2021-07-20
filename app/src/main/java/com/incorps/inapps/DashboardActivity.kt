package com.incorps.inapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.incorps.inapps.fragments.ChatFragment
import com.incorps.inapps.fragments.HomeFragment
import com.incorps.inapps.fragments.OrdersFragment
import com.incorps.inapps.fragments.ProfileFragment

class DashboardActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        loadFragment(HomeFragment())
        bottomNavigation.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment = HomeFragment()
        when(item.itemId) {
            R.id.navigation_home -> fragment = HomeFragment()
            R.id.navigation_order -> fragment = OrdersFragment()
            R.id.navigation_chat -> fragment = ChatFragment()
            R.id.navigation_profile -> fragment = ProfileFragment()
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        return true
    }

}