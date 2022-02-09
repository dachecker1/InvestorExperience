package com.vk.dachecker.investorsexperience.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.adapters.TickerCardAdapter
import com.vk.dachecker.investorsexperience.databinding.FragmentListBinding
import com.vk.dachecker.investorsexperience.db.Company
import com.vk.dachecker.investorsexperience.db.SharedViewModel
import com.vk.dachecker.investorsexperience.utils.ShareHelper


class ListFragment : Fragment(), TickerCardAdapter.OnTickerCardClickListener,
    TickerCardAdapter.ShareListener {
    private var binding: FragmentListBinding? = null
    private lateinit var viewModel: SharedViewModel
    private val adapter = TickerCardAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initAdMod()

        viewModel.companyListLiveData.observe(this, {
            TickerCardAdapter.result = it
        })

        binding?.apply {
            rcView.layoutManager = LinearLayoutManager(context)
            rcView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding!!.adView3.resume()
    }

    override fun onPause() {
        super.onPause()
        binding!!.adView3.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.adView3.destroy()
        binding = null
        viewModel.companyListLiveData.removeObservers(this)
    }

    override fun onItemClick(item: Company) {
        Toast.makeText(context, item.ticker, Toast.LENGTH_SHORT).show()
        val url: String = item.url.substringAfter('=')
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("$URL_YOUTUBE$url")
        )
        this.startActivity(webIntent)
    }

    override fun onShareClick(item: Company) {
        startActivity(
            Intent.createChooser(
                ShareHelper.shareTickerVideo(
                    item
                ), getString(R.string.share_by)
            )
        )
    }

    private fun initAdMod() {
        MobileAds.initialize(requireActivity()) //передать context
        val adRequest = AdRequest.Builder().build()
        binding!!.adView3.loadAd(adRequest)
    }

    companion object {
        const val URL_YOUTUBE = "http://www.youtube.com/watch?v="
        fun newInstance() = ListFragment()
    }
}