package com.incorps.inapps.fragments.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.incorps.inapps.CartActivity
import com.incorps.inapps.R

class ProcessedOrderFragment : Fragment() {
    private lateinit var btnCart: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_processed_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnCart = view.findViewById(R.id.btn_back_to_menu)
        btnCart.setOnClickListener {
            startActivity(Intent(context, CartActivity::class.java))
        }
    }
}