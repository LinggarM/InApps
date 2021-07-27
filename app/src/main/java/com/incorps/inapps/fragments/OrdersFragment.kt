package com.incorps.inapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.incorps.inapps.R
import com.incorps.inapps.fragments.orders.CompletedOrderFragment
import com.incorps.inapps.fragments.orders.OrderedFragment
import com.incorps.inapps.fragments.orders.ProcessedOrderFragment


class OrdersFragment : Fragment() {
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        tabLayout = view.findViewById(R.id.tl_orders)
        viewPager = view.findViewById(R.id.vp_orders)

        swipeRefresh.setOnRefreshListener {
            val ft = requireFragmentManager().beginTransaction()
            ft.detach(this).attach(this).commit()

            swipeRefresh.isRefreshing = false
        }

        // Setting View Pager Adapter
        val vpAdapter = ViewPagerAdapter(childFragmentManager)
        vpAdapter.addFragment(OrderedFragment(), "Dipesan")
        vpAdapter.addFragment(ProcessedOrderFragment(), "Diproses")
        vpAdapter.addFragment(CompletedOrderFragment(), "Selesai")

        viewPager.adapter = vpAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return 3 //three fragments
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}