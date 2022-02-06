package com.vk.dachecker.investorsexperience.db

import java.io.Serializable

data class Company(
    val date: String,
    val ticker: String,
    val description: String,
    val url:String
) : Serializable