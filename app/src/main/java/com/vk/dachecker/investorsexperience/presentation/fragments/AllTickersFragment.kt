package com.vk.dachecker.investorsexperience.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.presentation.adapters.AllTickersAdapter
import com.vk.dachecker.investorsexperience.databinding.FragmentAllTickersBinding
import com.vk.dachecker.investorsexperience.presentation.SharedViewModel

class AllTickersFragment : Fragment(), AllTickersAdapter.OnTickerCardClickListener {
    private var binding: FragmentAllTickersBinding? = null
    private val adapter = AllTickersAdapter(this)
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTickersBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        binding?.apply {
            rcView.layoutManager = GridLayoutManager(context, 4)
            rcView.adapter = adapter
        }
        viewModel.getOnlyTickerList().observe(this, {tickerList: List<String> ->
            adapter.itemList = tickerList
        })
    }

    override fun onItemClick(item: String) {
        viewModel.getSortedListByTicker(item)
        viewModel.setTickerName(item)
        parentFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, SearchTickerResultFragment.newInstance())
            .commit()
    }

    companion object {
        fun newInstance() = AllTickersFragment()
    }
}