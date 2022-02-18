package com.vk.dachecker.investorsexperience.presentation.activities.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.presentation.fragments.SearchFragment
import com.vk.dachecker.investorsexperience.databinding.ActivityMainBinding
import com.vk.dachecker.investorsexperience.presentation.SharedViewModel
import com.vk.dachecker.investorsexperience.presentation.fragments.AboutFragment
import com.vk.dachecker.investorsexperience.presentation.fragments.LinksFragment
import com.vk.dachecker.investorsexperience.presentation.fragments.ListFragment


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var viewModel: SharedViewModel
    private var isListFragmentOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)


//        (application as AppMainState).showAdIfAvailable(this){
//          активировать эти строчки, чтоб заработала реклама при старте приложения
//        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, SearchFragment())
            .commit()

        binding?.bNav?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    isListFragmentOpen = false
                    openFrag(SearchFragment.newInstance(), R.id.placeHolder)
                }
                R.id.list -> {
                    if(viewModel.getCompanyList().value?.isNotEmpty() == true) {
                        if (!isListFragmentOpen) {
                            openFrag(ListFragment.newInstance(), R.id.placeHolder)
                            isListFragmentOpen = true
                        }
                    } else {
                        Toast.makeText(this, R.string.bd_didnt_download, Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f)
            .commit()
    }
}