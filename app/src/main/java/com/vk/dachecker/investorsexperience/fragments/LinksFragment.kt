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
    private var binding: FragmentLinksBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinksBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            cvBroker.setOnClickListener {
                openActivity(URL_BROKER)
            }

            cvCourse1.setOnClickListener {
                openActivity(URL_COURSE1)
            }

            cvCourse2.setOnClickListener {
                openActivity(URL_COURSE2)
            }

            cvTelega.setOnClickListener {
                openActivity(URL_TELEGRAM)
            }

            cvYoutube.setOnClickListener {
                openActivity(URL_YOUTUBE_CHANNEL)
            }
        }
    }

    private fun openActivity(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = LinksFragment()
        private const val URL_BROKER = "https://www.tinkoff.ru/sl/35K9sv6xl3C"
        private const val URL_COURSE1 = "https://xn--80adjkzeedoigf4i.xn--p1ai/course1"
        private const val URL_COURSE2 = "https://xn--80adjkzeedoigf4i.xn--p1ai/course2"
        private const val URL_TELEGRAM = "https://t.me/investopit"
        private const val URL_YOUTUBE_CHANNEL =
            "https://www.youtube.com/channel/UC7RMUZkM3FPGFgQXqN8gB9A"
    }
}