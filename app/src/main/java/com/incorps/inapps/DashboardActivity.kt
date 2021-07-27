package com.incorps.inapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.incorps.inapps.fragments.ChatFragment
import com.incorps.inapps.fragments.HomeFragment
import com.incorps.inapps.fragments.OrdersFragment
import com.incorps.inapps.fragments.ProfileFragment
import com.incorps.inapps.preferences.AccountSessionPreferences

class DashboardActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var accountSessionPreferences: AccountSessionPreferences

    private lateinit var bottomNavigation: BottomNavigationView
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Set Bottom Navigation
        bottomNavigation = findViewById(R.id.bottom_navigation)
        loadFragment(HomeFragment())
        bottomNavigation.setOnItemSelectedListener(this)

//        // Check Login Info
//        accountSessionPreferences = AccountSessionPreferences(this)
//        if (!accountSessionPreferences.isLogin) {
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//        }
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

    override fun onBackPressed() {

        // 2000 = 2 seconds
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            Toast.makeText(this, resources.getString(R.string.back_pressed_dashboard), Toast.LENGTH_SHORT).show()
        }

        backPressedTime = System.currentTimeMillis()

    }

}