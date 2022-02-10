package com.vk.dachecker.investorsexperience.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vk.dachecker.investorsexperience.data.entities.Library
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = arrayOf(Library::class), version = 1
)
abstract class MainDataBase : RoomDatabase() {
    abstract fun getDao() : Dao

    companion object {
        @Volatile private var INSTANCE: MainDataBase? = null

        @InternalCoroutinesApi
        fun getDataBase(context: Context) : MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDataBase::class.java,
                    "library.db"
                ).build()
                instance
            }
        }
    }
}