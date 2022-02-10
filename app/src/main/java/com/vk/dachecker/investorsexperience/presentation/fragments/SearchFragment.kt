package com.vk.dachecker.investorsexperience.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.vk.dachecker.investorsexperience.presentation.SharedViewModel
import com.vk.dachecker.investorsexperience.utils.InternetConnection
import kotlinx.coroutines.InternalCoroutinesApi


class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private lateinit var viewModel : SharedViewModel
    private var isOnline = InternetConnection()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding!!.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        initAdMod()
        if(isOnline.checkInternetConnection(requireContext())){
            binding?.tvInternerConnection?.visibility = View.GONE
        } else {
            binding?.tvInternerConnection?.visibility = View.VISIBLE
        }

        binding?.run {
            cvSearch.setOnClickListener {
                val ticker = edTicker.text.toString()
                if (ticker.isEmpty()) {
                    Toast.makeText(context, getText(R.string.empty_text), Toast.LENGTH_SHORT).show()
                } else {
                    cvSearch.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                    viewModel.getSortedListByTicker(ticker)
                    viewModel.tickerName.value = ticker

                    viewModel.getSelectedTicker().observe(this@SearchFragment, {
                        if (it.isEmpty()) {
                            imLogo.setImageResource(R.drawable.ic_empty_result)
                            tvTitle.text = getString(R.string.empty_result)
                            cvSearch.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        } else {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.placeHolder, SearchTickerResultFragment.newInstance())
                                .commit()
                        }
                    })
                }
            }
        }

        binding?.cvTicker?.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.placeHolder, AllTickersFragment.newInstance())
                    .commit()
        }

        binding?.cvReferal?.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(SOLODIN_APP_LINK))
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.adView?.resume()
    }

    override fun onPause() {
        super.onPause()
        binding?.adView?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.adView?.destroy()
        binding = null
    }

    private fun initAdMod(){
        MobileAds.initialize(requireActivity()) //передать context
        val adRequest = AdRequest.Builder().build()
        binding?.adView?.loadAd(adRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
        const val SOLODIN_APP_LINK = "https://play.google.com/store/apps/details?id=com.vk.dachecker.solodinstocksearch"
    }

}