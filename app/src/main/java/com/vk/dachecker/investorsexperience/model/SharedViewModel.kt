package com.vk.dachecker.investorsexperience.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.vk.dachecker.investorsexperience.repositories.StockRepository
import kotlinx.coroutines.*


class SharedViewModel(application: Application) : AndroidViewModel(application) {
    var job: Job? = null
    private val repo = StockRepository.getInstanse()
    //    список всех выпусков, с тикерами и без
    val companyListLiveData = MutableLiveData<ArrayList<Company>>()
    //список выпусков, в которых есть только тикеры
    val listOfStockLiveData = MutableLiveData<ArrayList<Company>>()
    //список выпусков по заданному тикеру
    val sortedListOfStockLiveData = MutableLiveData<ArrayList<Company>>()
    val onlyTickerListLivedata = MutableLiveData<List<String>>()
    val tickerName = MutableLiveData<String>()

    suspend fun downloadDataBase() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repo.getStockListFromDataBase()
            companyListLiveData.postValue(response.companyList)
            listOfStockLiveData.postValue(response.listOfStock)
            onlyTickerListLivedata.postValue(response.onlyTickerList)
        }
    }

    fun getCompanyList(): ArrayList<Company> {
        return companyListLiveData.value as ArrayList<Company>
    }

    fun getListOfStock(): ArrayList<Company> {
        return listOfStockLiveData.value as ArrayList<Company>
    }

    fun getSortedListByTicker(ticker: String): ArrayList<Company> {
        repo.getSortedListByTicker(ticker) { listCompany ->
            sortedListOfStockLiveData.value = listCompany as ArrayList<Company>
        }
        return sortedListOfStockLiveData.value as ArrayList<Company>
    }

    fun getSortedTickerList(): List<String>{
        return onlyTickerListLivedata.value as List<String>
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
