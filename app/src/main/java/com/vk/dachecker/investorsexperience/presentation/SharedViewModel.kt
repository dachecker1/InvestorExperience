package com.vk.dachecker.investorsexperience.presentation

import android.app.Application
import androidx.lifecycle.*
import com.vk.dachecker.investorsexperience.data.model.Company
import com.vk.dachecker.investorsexperience.data.repositories.StockRepository
import kotlinx.coroutines.*


class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = StockRepository.getInstanse()

    //    список всех выпусков, с тикерами и без
    private val companyListLiveData = MutableLiveData<ArrayList<Company>>()

    //список выпусков, в которых есть только тикеры
    private val listOfStockLiveData = MutableLiveData<ArrayList<Company>>()

    //список выпусков по заданному тикеру
    private val sortedListOfStockLiveData = MutableLiveData<ArrayList<Company>>()
    private val onlyTickerListLivedata = MutableLiveData<List<String>>()
    val tickerName = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            val response = repo.getStockListFromDataBase()
            companyListLiveData.postValue(response.companyList)
            listOfStockLiveData.postValue(response.listOfStock)
            onlyTickerListLivedata.postValue(response.onlyTickerList)
        }
    }

    fun getCompanyList() : LiveData<ArrayList<Company>>{
        return companyListLiveData
    }

    fun getSortedListByTicker(ticker: String): ArrayList<Company> {
        repo.getSortedListByTicker(ticker) { listCompany ->
            sortedListOfStockLiveData.value = listCompany as ArrayList<Company>
        }
        return sortedListOfStockLiveData.value as ArrayList<Company>
    }

    fun getSelectedTicker() : LiveData<ArrayList<Company>>{
        return sortedListOfStockLiveData
    }

    fun getOnlyTickerList(): LiveData<List<String>> {
        return onlyTickerListLivedata
    }
}
