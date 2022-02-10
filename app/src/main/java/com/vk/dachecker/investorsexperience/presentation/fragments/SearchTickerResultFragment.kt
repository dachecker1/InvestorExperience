package com.vk.dachecker.investorsexperience.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.presentation.adapters.TickerCardAdapter
import com.vk.dachecker.investorsexperience.databinding.FragmentSearchTickerResultBinding
import com.vk.dachecker.investorsexperience.data.model.Company
import com.vk.dachecker.investorsexperience.presentation.SharedViewModel
import com.vk.dachecker.investorsexperience.utils.ShareHelper

class SearchTickerResultFragment : Fragment(), TickerCardAdapter.OnTickerCardClickListener,
    TickerCardAdapter.ShareListener {
    private var binding: FragmentSearchTickerResultBinding? = null
    private val adapter = TickerCardAdapter(this, this)
    private lateinit var viewModel: SharedViewModel
    private var iAd: InterstitialAd? = null
//    private var adShowCounter = 0
//    private val adShowCounterMax = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTickerResultBinding.inflate(layoutInflater)
        loadInterAd()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initAdMod()

        viewModel.getTickerName().observe(this, {
            binding?.tvTicker?.text = it.uppercase()
        })

        viewModel.getSelectedTicker().observe(activity as LifecycleOwner, {
            TickerCardAdapter.result = it
        })

        binding?.apply {
            rcView.layoutManager = LinearLayoutManager(context)
            rcView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.adView2?.resume()
    }

    override fun onPause() {
        super.onPause()
        binding?.adView2?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.adView2?.destroy()
        binding = null
    }

    override fun onItemClick(company: Company) {
        showInterAd(object : AdListener {
            override fun onFinish() {
                Toast.makeText(context, company.ticker, Toast.LENGTH_SHORT).show()
                val url: String = company.url.substringAfter('=')
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("${ListFragment.URL_YOUTUBE}$url")
                )
                startActivity(webIntent)
            }
        })
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

    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(context, getString(R.string.inter_ad_id), request,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(ad: LoadAdError) {
                    iAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    iAd = ad
                }
            })
    }

    private fun showInterAd(adListener: AdListener) {
        if (iAd != null) {
            iAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    iAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                }
            }
//            adShowCounter = 0
            iAd?.show(requireActivity())
        } else {
//            adShowCounter++
            adListener.onFinish()
        }
    }

    private fun initAdMod() {
        MobileAds.initialize(requireActivity()) //передать context
        val adRequest = AdRequest.Builder().build()
        binding!!.adView2.loadAd(adRequest)
    }

    interface AdListener {
        fun onFinish()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchTickerResultFragment()
    }
}