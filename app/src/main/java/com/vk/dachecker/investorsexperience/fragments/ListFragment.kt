package com.vk.dachecker.investorsexperience.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.adapters.TickerCardAdapter
import com.vk.dachecker.investorsexperience.databinding.FragmentListBinding
import com.vk.dachecker.investorsexperience.db.Company
import com.vk.dachecker.investorsexperience.db.SharedViewModel


class ListFragment : Fragment(), TickerCardAdapter.OnTickerCardClickListener {
    private var binding : FragmentListBinding? = null
    private lateinit var viewModel : SharedViewModel
    private val adapter = TickerCardAdapter(this)


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

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onItemClick(item: Company) {
        Toast.makeText(context,item.ticker, Toast.LENGTH_SHORT).show()
        val uri = Uri.parse(item.url)
        val url : String = item.url.substringAfter('=')
        val id = uri.getQueryParameter("v")
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$url"))
        this.startActivity(webIntent)
    }

    private fun initAdMod(){
        MobileAds.initialize(requireActivity()) //передать context
        val adRequest = AdRequest.Builder().build()
        binding!!.adView3.loadAd(adRequest)
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
    }
}