package com.vk.dachecker.investorsexperience.model.retrofit

import com.vk.dachecker.investorsexperience.model.Company
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {
    @GET("exec")
    fun getStockList(): Call<CompanyResponse>
}