package com.vk.dachecker.investorsexperience.presentation.activities.activities

import android.app.Application
import com.vk.dachecker.investorsexperience.data.db.MainDataBase
import kotlinx.coroutines.InternalCoroutinesApi

class MainApp: Application() {
    @InternalCoroutinesApi
    val database by lazy { MainDataBase.getDataBase(this)}
}