package com.incorps.inapps

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
    private lateinit var btnNext: Button

    private var fragmentSlider1 = IntroSliderFragment()
    private var fragmentSlider2 = IntroSliderFragment()
    private var fragmentSlider3 = IntroSliderFragment()

    private lateinit var dot1: ImageView
    private lateinit var dot2: ImageView
    private lateinit var dot3: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)

        // Intro Slider Only Appear Once at First Time
        introSliderPreferences = IntroSliderPreferences(this)
        if (!introSliderPreferences.isFirstTimeLaunch) {
            launchDashboard()
            finish()
        }

        viewPager = findViewById(R.id.viewpager_introslider)
        linearLayoutDots = findViewById(R.id.linear_layout_dots)
        btnNext = findViewById(R.id.btn_next)

        dot1 = findViewById(R.id.dots1)
        dot2 = findViewById(R.id.dots2)
        dot3 = findViewById(R.id.dots3)

        dot1.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
        dot2.setColorFilter(resources.getColor(R.color.white_bottom_nav))
        dot3.setColorFilter(resources.getColor(R.color.white_bottom_nav))

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

        btnNext.setOnClickListener {
            viewPager.currentItem++
        }

        viewPager.adapter = introViewPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {

                        // button setting
                        btnNext.text = resources.getString(R.string.text_continue)
                        btnNext.setOnClickListener {
                            viewPager.currentItem++
                        }

                        // dots color
                        dot1.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
                        dot2.setColorFilter(resources.getColor(R.color.white_bottom_nav))
                        dot3.setColorFilter(resources.getColor(R.color.white_bottom_nav))
                    }
                    1 -> {

                        // button setting
                        btnNext.text = resources.getString(R.string.text_continue)
                        btnNext.setOnClickListener {
                            viewPager.currentItem++
                        }

                        // dots color
                        dot1.setColorFilter(resources.getColor(R.color.white_bottom_nav))
                        dot2.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
                        dot3.setColorFilter(resources.getColor(R.color.white_bottom_nav))
                    }
                    2 -> {

                        // button setting
                        btnNext.text = resources.getString(R.string.start)
                        btnNext.setOnClickListener {
                            launchDashboard()
                        }

                        // dots color
                        dot1.setColorFilter(resources.getColor(R.color.white_bottom_nav))
                        dot2.setColorFilter(resources.getColor(R.color.white_bottom_nav))
                        dot3.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
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