package com.vk.dachecker.investorsexperience.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.databinding.FragmentSearchBinding
import com.vk.dachecker.investorsexperience.db.SharedViewModel
import kotlinx.coroutines.InternalCoroutinesApi


class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private lateinit var viewModel : SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        //заглушить кнопку поиска по готовой базе
        return binding!!.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initAdMod()

        binding?.cvSearch?.setOnClickListener {
            val ticker = binding!!.edTicker.text.toString()
            if (ticker.isEmpty()) {
                Toast.makeText(context, getText(R.string.empty_text), Toast.LENGTH_SHORT).show()
            } else {
                binding!!.cvSearch.visibility = View.INVISIBLE
                binding!!.progressBar.visibility = View.VISIBLE
                viewModel.getSortedListByTicker(ticker)
                viewModel.tickerName.value = ticker

                viewModel.sortedListOfStockLiveData.observe(this, {
                    if (it.isEmpty()) {
                        binding!!.imLogo.setImageResource(R.drawable.ic_empty_result)
                        binding!!.tvTitle.text = getString(R.string.empty_result)
                        binding!!.cvSearch.visibility = View.VISIBLE
                        binding!!.progressBar.visibility = View.GONE
                    } else {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.placeHolder, SearchTickerResultFragment.newInstance())
                            .commit()
                    }
                })
            }
        }


        binding?.cvTicker?.isActivated = viewModel.listOfStockLiveData.value?.isNotEmpty() == true
        binding?.cvTicker?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, AllTickersFragment.newInstance())
                .commit()
        }

        binding?.cvReferal?.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.vk.dachecker.solodinstocksearch"))
            startActivity(i)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private fun initAdMod(){
        MobileAds.initialize(requireActivity()) //передать context
        val adRequest = AdRequest.Builder().build()
        binding!!.adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        binding!!.adView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding!!.adView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!.adView.destroy()
        binding = null
    }

}