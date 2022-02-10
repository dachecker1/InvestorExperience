package com.vk.dachecker.investorsexperience.data.repositories

import android.util.Log
import com.vk.dachecker.investorsexperience.data.model.Company
import com.vk.dachecker.investorsexperience.data.model.CompanyLists
import com.vk.dachecker.investorsexperience.data.retrofit.RetrofitClient
import com.vk.dachecker.investorsexperience.data.retrofit.RetrofitServices

class StockRepository() {

    var service: RetrofitServices = RetrofitClient.getClient()
    private val companyList = arrayListOf<Company>()
    private val listOfStock = arrayListOf<Company>()
    private val sortedListOfStock = arrayListOf<Company>()

    suspend fun getStockListFromDataBase(): CompanyLists {
        val response = service.getStockList()
        if (response.isSuccessful) {
            val list = response.body()
            for (i in 0 until (list?.items?.size?.minus(1)!!)) {
                val companyObject = list.items.get(i)
                val company = Company(
                    companyObject.date,
                    companyObject.ticker,
                    companyObject.description.substringAfter(" "),
                    companyObject.url
                )
                if (companyObject.ticker.isEmpty()) {
                    companyList.add(company)
                } else {
                    listOfStock.add(company)
                    companyList.add(company)
                }
            }
        } else {
            Log.d("MyTag", "Загрузка БД не увенчалась успехом")
        }
        val onlyTickerList = mutableListOf<String>()
        for (i in 0 until listOfStock.size - 1) {
            onlyTickerList.add(listOfStock[i].ticker)
        }
        val distinctListOfStock = onlyTickerList.distinct().sorted()
        return CompanyLists(
            companyList,
            listOfStock,
            distinctListOfStock
        )
    }

    fun getSortedListByTicker(ticker: String, sortedListCallBack: (List<Company>) -> Unit) {
        sortedListOfStock.clear()
        for (i in 0 until listOfStock.size - 1) {
            if (listOfStock[i].ticker.equals(ticker, ignoreCase = true)) {
                sortedListOfStock.add(listOfStock[i])
            }
        }
        sortedListCallBack.invoke(sortedListOfStock)
    }

    companion object {
        private var repo: StockRepository? = null
        fun getInstanse() = synchronized(this) {
            if (repo == null)
                repo = StockRepository()
            repo as StockRepository
        }
    }
}
