package com.vk.dachecker.investorsexperience.model.retrofit

object Common {
    private val BASE_URL =
        "https://script.google.com/macros/s/AKfycbye-pDB2Et_0OCH4rnAIuisMvf7rgNtS3qSr5EK2suDRdAAPbkj/"
    val retrofitServices: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}