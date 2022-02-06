package com.vk.dachecker.investorsexperience.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.databinding.FragmentLinksBinding


class LinksFragment : Fragment() {
    private var binding : FragmentLinksBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinksBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            cvBroker.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tinkoff.ru/sl/35K9sv6xl3C"))
                startActivity(i)
            }

            cvCourse1.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://xn--80adjkzeedoigf4i.xn--p1ai/course1"))
                startActivity(i)
            }

            cvCourse2.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://xn--80adjkzeedoigf4i.xn--p1ai/course2"))
                startActivity(i)
            }

            cvTelega.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/investopit"))
                startActivity(i)
            }

            cvYoutube.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC7RMUZkM3FPGFgQXqN8gB9A"))
                startActivity(i)
            }
        }


    }

    companion object {
        fun newInstance() = LinksFragment()
    }
}