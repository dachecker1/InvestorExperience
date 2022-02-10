package com.vk.dachecker.investorsexperience.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.fragments.SearchFragment
import com.vk.dachecker.investorsexperience.databinding.ActivityMainBinding
import com.vk.dachecker.investorsexperience.model.SharedViewModel
import com.vk.dachecker.investorsexperience.fragments.AboutFragment
import com.vk.dachecker.investorsexperience.fragments.LinksFragment
import com.vk.dachecker.investorsexperience.fragments.ListFragment
import com.vk.dachecker.investorsexperience.repositories.StockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel
    private var isListFragmentOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch{
            viewModel.downloadDataBase()
        }

//        (application as AppMainState).showAdIfAvailable(this){
//
//        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, SearchFragment())
            .commit()

        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    isListFragmentOpen = false
//                    viewModel.sortedListOfStockLiveData.value = arrayListOf()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.placeHolder, SearchFragment())
                        .commit()
                }
                R.id.list -> {
                    if (!isListFragmentOpen) {
                        openFrag(ListFragment.newInstance(), R.id.placeHolder)
                        isListFragmentOpen = true
                    }
                }
                R.id.links -> {
                    isListFragmentOpen = false
                    openFrag(LinksFragment.newInstance(), R.id.placeHolder)
                }
                R.id.about -> {
                    isListFragmentOpen = false
                    openFrag(AboutFragment.newInstance(), R.id.placeHolder)
                }
            }
            true
        }
    }

    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f)
            .commit()
    }
}