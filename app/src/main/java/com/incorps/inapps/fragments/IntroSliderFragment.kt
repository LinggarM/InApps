package com.incorps.inapps.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.incorps.inapps.R

class IntroSliderFragment : Fragment() {

    private lateinit var titleFragment: TextView
    private lateinit var descFragment: TextView
    private lateinit var imgFragment: ImageView
    private var title: String = ""
    private var desc: String = ""
    private var img: Int = R.drawable.img_introslider1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleFragment = view.findViewById(R.id.tv_title)
        descFragment = view.findViewById(R.id.tv_desc)
        imgFragment = view.findViewById(R.id.img_slide)
        titleFragment.text = title
        descFragment.text = desc
        imgFragment.setImageDrawable(resources.getDrawable(img))
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDesc(desc: String) {
        this.desc = desc
    }

    fun setImage(img: Int) {
        this.img = img
    }
}