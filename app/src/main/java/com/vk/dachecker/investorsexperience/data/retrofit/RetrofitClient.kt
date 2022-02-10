package com.vk.dachecker.investorsexperience.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL =
        "https://script.google.com/macros/s/AKfycbye-pDB2Et_0OCH4rnAIuisMvf7rgNtS3qSr5EK2suDRdAAPbkj/"
    private var retrofit : RetrofitServices? = null

    fun getClient(): RetrofitServices {
        if(retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitServices::class.java)
        }
        return retrofit!!
    }
}