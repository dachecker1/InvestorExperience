package com.vk.dachecker.investorsexperience.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vk.dachecker.investorsexperience.model.Company
import com.vk.dachecker.investorsexperience.model.SharedViewModel
import com.vk.dachecker.investorsexperience.model.retrofit.Common
import com.vk.dachecker.investorsexperience.model.retrofit.CompanyResponse
import retrofit2.Callback
import com.vk.dachecker.investorsexperience.model.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Response

class StockRepository() {

    var mService: RetrofitServices = Common.retrofitServices
    private val companyList = arrayListOf<Company>()
    private val listOfStock = arrayListOf<Company>()
    private val sortedListOfStock = arrayListOf<Company>()


    fun getStockListFromDataBase(
        companyListCallBack: (List<Company>) -> Unit,
        listOfStockCallBack: (List<Company>) -> Unit
    ) {
        listOfStock.clear()
        mService.getStockList().enqueue(object : Callback<CompanyResponse> {
            override fun onResponse(
                call: Call<CompanyResponse>,
                response: Response<CompanyResponse>
            ) {
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
                companyListCallBack.invoke(companyList)
                listOfStockCallBack.invoke(listOfStock)
            }

            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                Log.d("MyTag", "Загрузка БД не увенчалась успехом")
                Log.d("MyTag", t.toString())
            }
        })
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

    //метод, который создает неповторяющийся список тикеров, сортировка по алфавиту
    //используется в SearchFragment при нажатии на кнопку cvTicker
    fun getTickerList() : List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until listOfStock.size - 1) {
            list.add(listOfStock[i].ticker)
        }
        val distinctListOfStock = list.distinct()
        return distinctListOfStock.sorted()

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