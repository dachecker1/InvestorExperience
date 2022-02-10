package com.vk.dachecker.investorsexperience.model

data class CompanyLists(
    val companyList: ArrayList<Company>,
    val listOfStock: ArrayList<Company>,
    val onlyTickerList: List<String>
)
