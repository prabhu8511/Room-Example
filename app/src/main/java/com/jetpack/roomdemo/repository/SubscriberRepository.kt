package com.jetpack.roomdemo.repository

import com.jetpack.roomdemo.db.Subscriber
import com.jetpack.roomdemo.db.SubscriberDAO

class SubscriberRepository(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber):Long{
        return dao.addSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int{
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int{
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}