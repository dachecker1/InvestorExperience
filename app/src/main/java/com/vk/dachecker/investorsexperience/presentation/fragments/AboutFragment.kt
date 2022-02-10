package com.vk.dachecker.investorsexperience.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vk.dachecker.investorsexperience.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    private var binding: FragmentAboutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}