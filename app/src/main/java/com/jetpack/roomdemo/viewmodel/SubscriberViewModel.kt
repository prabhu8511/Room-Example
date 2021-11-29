package com.jetpack.roomdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.roomdemo.db.Subscriber
import com.jetpack.roomdemo.repository.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel (private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()
    val saveOrUpdateButtonText = MutableLiveData<String>();
    val clearAllOrDeleteButtonText = MutableLiveData<String>();

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        addSubscribers(Subscriber(0, name, email))
        inputName.value = null
        inputEmail.value = null
    }

    fun clearAllOrDelete() {
        deleteAllSubscribers()
    }

    private fun addSubscribers(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.insert(subscriber)
    }

    fun updateSubscribers(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.update(subscriber)
    }

    fun deleteSubscribers(subscriber: Subscriber): Job = viewModelScope.launch {
        repository.delete(subscriber)
    }

    private fun deleteAllSubscribers(): Job = viewModelScope.launch {
        repository.deleteAll()
    }
}