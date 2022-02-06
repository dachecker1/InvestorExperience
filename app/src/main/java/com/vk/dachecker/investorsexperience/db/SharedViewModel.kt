package com.vk.dachecker.investorsexperience.db

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.vk.dachecker.investorsexperience.R
import com.vk.dachecker.investorsexperience.activities.MainActivity
import com.vk.dachecker.investorsexperience.entities.Library
import com.vk.dachecker.investorsexperience.fragments.SearchTickerResultFragment
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class SharedViewModel(application: Application) : AndroidViewModel(application) {
    val companyList = arrayListOf<Company>()
    val listOfStock = arrayListOf<Company>()
    val sortedListOfStock = arrayListOf<Company>()

    //список всех выпусков, с тикерами и без
    val companyListLiveData: MutableLiveData<ArrayList<Company>> by lazy {
        MutableLiveData<ArrayList<Company>>()
    }
    //список выпусков, в которых есть только тикеры
    val listOfStockLiveData: MutableLiveData<ArrayList<Company>> by lazy {
        MutableLiveData<ArrayList<Company>>()
    }
    //список выпусков по заданному тикеру
    val sortedListOfStockLiveData: MutableLiveData<ArrayList<Company>> by lazy {
        MutableLiveData<ArrayList<Company>>()
    }

    val tickerName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getSortedListByTicker(ticker: String) {
        sortedListOfStock.clear()
        for (i in 0 until listOfStock.size - 1) {
            if (listOfStock[i].ticker.equals(ticker, ignoreCase = true)) {
                sortedListOfStock.add(listOfStock[i])
            }
        }
        sortedListOfStockLiveData.value = sortedListOfStock
    }

    fun getFirstList() {
        val queue = Volley.newRequestQueue(getApplication())
        val url =
            "https://script.google.com/macros/s/AKfycbye-pDB2Et_0OCH4rnAIuisMvf7rgNtS3qSr5EK2suDRdAAPbkj/exec"
        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                val data = it.getJSONArray("items")
                for (i in 0 until data.length()) {
                    val companyJasonObject = data.getJSONObject(i)
                    val description =
                        companyJasonObject.getString("description").substringAfter(" ")
                    val companyObject = Company(
                        companyJasonObject.getString("date"),
                        companyJasonObject.getString("ticker"),
                        description,
                        companyJasonObject.getString("url"),
                    )
                    val ticker = companyJasonObject.getString("ticker")
                    if (ticker.isEmpty()) {
                        companyList.add(companyObject)
                    } else {
                        listOfStock.add(companyObject)
                        companyList.add(companyObject)
                    }
                }
                companyListLiveData.value = companyList
                listOfStockLiveData.value = listOfStock
                //view.enabled = false
            }, Response.ErrorListener { }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    return super.getHeaders()
                }
            }
        queue.add(jsonObjectRequest)
    }
}