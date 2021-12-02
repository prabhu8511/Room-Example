package com.jetpack.roomdemo.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.roomdemo.db.Subscriber
import com.jetpack.roomdemo.repository.SubscriberRepository
import com.jetpack.roomdemo.utils.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SubscriberViewModel (private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()
    val saveOrUpdateButtonText = MutableLiveData<String>();
    val clearAllOrDeleteButtonText = MutableLiveData<String>();
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    private val statusMessage = MutableLiveData<Event<String>>()

//    val message:LiveData<Event<String>>
//            get() = statusMessage

    fun getMessage():LiveData<Event<String>>{
        return statusMessage
    }

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        if(TextUtils.isEmpty(inputName.value))
            statusMessage.value = Event("Please enter subscriber's name")
        else if(TextUtils.isEmpty(inputEmail.value))
            statusMessage.value = Event("Please enter subscriber's email")
        else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches())
            statusMessage.value = Event("Please enter a correct email address")
        else{
            if(isUpdateOrDelete){
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                updateSubscribers(subscriberToUpdateOrDelete)
            }else{
                val name = inputName.value!!
                val email = inputEmail.value!!
                addSubscribers(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }


    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun clearAllOrDelete() {
        if(isUpdateOrDelete)
            deleteSubscribers(subscriberToUpdateOrDelete)
        else deleteAllSubscribers()
    }

    private fun addSubscribers(subscriber: Subscriber): Job = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if(newRowId>-1)
             statusMessage.value = Event("Subscriber Added successfully ")
        else  statusMessage.value = Event("Error Occurred")
    }

    private fun updateSubscribers(subscriber: Subscriber): Job = viewModelScope.launch {
        val noOfRows = repository.update(subscriber)
        if(noOfRows>0){
            statusMessage.value = Event("$noOfRows Row Updated successfully")
            resetView()
        }else  statusMessage.value = Event("Error Occurred")

    }

    private fun deleteSubscribers(subscriber: Subscriber): Job = viewModelScope.launch {
        val noDeleteRows = repository.delete(subscriber)
        if(noDeleteRows>0){
            statusMessage.value = Event("$noDeleteRows Row Deleted successfully")
            resetView()
        }else  statusMessage.value = Event("Error Occurred")

    }

    private fun deleteAllSubscribers(): Job = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All Subscribers Deleted successfully")
    }

    private fun resetView(){
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
}