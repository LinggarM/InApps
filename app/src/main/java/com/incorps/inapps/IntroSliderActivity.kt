package com.incorps.inapps

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.incorps.inapps.fragments.IntroSliderFragment
import com.incorps.inapps.preferences.IntroSliderPreferences

class IntroSliderActivity : AppCompatActivity() {

    private lateinit var introSliderPreferences: IntroSliderPreferences
    private lateinit var viewPager: ViewPager
    private lateinit var linearLayoutDots: LinearLayout
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button

    private var fragmentSlider1 = IntroSliderFragment()
    private var fragmentSlider2 = IntroSliderFragment()
    private var fragmentSlider3 = IntroSliderFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)

        introSliderPreferences = IntroSliderPreferences(this)
        if (!introSliderPreferences.isFirstTimeLaunch) {
            launchDashboard()
            finish()
        }

        viewPager = findViewById(R.id.viewpager_introslider)
        linearLayoutDots = findViewById(R.id.linear_layout_dots)
        btnBack = findViewById(R.id.btn_back)
        btnNext = findViewById(R.id.btn_next)

        fragmentSlider1.setTitle(resources.getString(R.string.introslider1_title))
        fragmentSlider2.setTitle(resources.getString(R.string.introslider2_title))
        fragmentSlider3.setTitle(resources.getString(R.string.introslider3_title))

        fragmentSlider1.setDesc(resources.getString(R.string.introslider1_desc))
        fragmentSlider2.setDesc(resources.getString(R.string.introslider2_desc))
        fragmentSlider3.setDesc(resources.getString(R.string.introslider3_desc))

        fragmentSlider1.setImage(R.drawable.img_introslider1)
        fragmentSlider2.setImage(R.drawable.img_introslider2)
        fragmentSlider3.setImage(R.drawable.img_introslider3)

        // Setting Adapter
        val introViewPagerAdapter = IntroViewPagerAdapter(supportFragmentManager)
        introViewPagerAdapter.addFragment(fragmentSlider1)
        introViewPagerAdapter.addFragment(fragmentSlider2)
        introViewPagerAdapter.addFragment(fragmentSlider3)

        btnBack.setOnClickListener {
            viewPager.currentItem--
        }
        btnNext.setOnClickListener {
            viewPager.currentItem++
        }

        viewPager.adapter = introViewPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        btnBack.visibility = View.GONE
                        btnNext.text = resources.getString(R.string.next)
                        btnNext.setOnClickListener {
                            viewPager.currentItem++
                        }
                    }
                    1 -> {
                        btnBack.visibility = View.VISIBLE
                        btnNext.text = resources.getString(R.string.next)
                        btnNext.setOnClickListener {
                            viewPager.currentItem++
                        }
                    }
                    2 -> {
                        btnBack.visibility = View.VISIBLE
                        btnNext.text = resources.getString(R.string.start)
                        btnNext.setOnClickListener {
                            launchDashboard()
                        }
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun launchDashboard() {
        introSliderPreferences.isFirstTimeLaunch = false
        val intentDashboardActivity = Intent(this, DashboardActivity::class.java)
        startActivity(intentDashboardActivity)
        finish()
    }

    class IntroViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

    }

}