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
    private lateinit var btnLine: MaterialCardView
    private lateinit var btnInstagram: MaterialCardView

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
        btnLine = view.findViewById(R.id.btn_line)
        btnInstagram = view.findViewById(R.id.btn_instagram)

        btnWhatsapp.setOnClickListener {
            val uriWhatsapp = Uri.parse(resources.getString(R.string.link_whatsapp))
            startActivity(Intent(Intent.ACTION_VIEW, uriWhatsapp))
        }

        btnLine.setOnClickListener {
            val uriLine = Uri.parse(resources.getString(R.string.link_line))
            startActivity(Intent(Intent.ACTION_VIEW, uriLine))
        }

        btnInstagram.setOnClickListener {
            val uriInstagram = Uri.parse(resources.getString(R.string.link_instagram))
            startActivity(Intent(Intent.ACTION_VIEW, uriInstagram))
        }
    }
}