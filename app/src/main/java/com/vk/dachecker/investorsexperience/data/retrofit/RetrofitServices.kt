package com.vk.dachecker.investorsexperience.data.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitServices {
    @GET("exec")
    suspend fun getStockList(): Response<CompanyResponse>
}