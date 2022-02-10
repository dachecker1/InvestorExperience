package com.vk.dachecker.investorsexperience.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vk.dachecker.investorsexperience.data.entities.Library
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM library")
    fun getAll(): Flow<List<Library>>

    @Insert
    suspend fun insert(item : Library)

    @Update
    suspend fun update(item: Library)
}