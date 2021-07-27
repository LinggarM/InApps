package com.incorps.inapps.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.card.MaterialCardView
import com.incorps.inapps.R

class ChatFragment : Fragment() {

    private lateinit var btnWhatsapp: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnWhatsapp = view.findViewById(R.id.btn_whatsapp)
        btnWhatsapp.setOnClickListener {
            val uriWhatsapp = Uri.parse(resources.getString(R.string.link_whatsapp))
            startActivity(Intent(Intent.ACTION_VIEW, uriWhatsapp))
        }
    }
}