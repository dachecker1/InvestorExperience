package com.vk.dachecker.investorsexperience.activities

import android.app.Application
import com.vk.dachecker.investorsexperience.db.MainDataBase
import kotlinx.coroutines.InternalCoroutinesApi

class MainApp: Application() {
    @InternalCoroutinesApi
    val database by lazy { MainDataBase.getDataBase(this)}
}