package com.vk.dachecker.investorsexperience.model

import android.app.Application
import androidx.lifecycle.*
import com.vk.dachecker.investorsexperience.repositories.StockRepository
import kotlinx.coroutines.launch


class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = StockRepository.getInstanse()

    //    список всех выпусков, с тикерами и без
    val companyListLiveData = MutableLiveData<ArrayList<Company>>()

    //список выпусков, в которых есть только тикеры
    val listOfStockLiveData = MutableLiveData<ArrayList<Company>>()

    //список выпусков по заданному тикеру
    val sortedListOfStockLiveData = MutableLiveData<ArrayList<Company>>()
    val tickerName = MutableLiveData<String>()

    fun downloadDataBase() {
        repo.getStockListFromDataBase(
            { companyListLiveData.value = it as ArrayList<Company> },
            { listOfStockLiveData.value = it as ArrayList<Company> }
        )
    }

    fun getCompanyList() : ArrayList<Company>{
        return companyListLiveData.value as ArrayList<Company>
    }

    fun getListOfStock(): ArrayList<Company>{
        return listOfStockLiveData.value as ArrayList<Company>
    }


    fun getSortedListByTicker(ticker: String): ArrayList<Company> {
        repo.getSortedListByTicker(ticker) { listCompany ->
            sortedListOfStockLiveData.value = listCompany as ArrayList<Company>
        }
        return sortedListOfStockLiveData.value as ArrayList<Company>
    }

    fun getSortedTickerList(): List<String> {
        return repo.getTickerList()
    }

//    companion object {
//        const val GOOGLE_SHEET_DATABASE =
//            "https://script.google.com/macros/s/AKfycbye-pDB2Et_0OCH4rnAIuisMvf7rgNtS3qSr5EK2suDRdAAPbkj/exec"
//    }
}
