package com.jetpack.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun addSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) : Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers():LiveData<List<Subscriber>>
}