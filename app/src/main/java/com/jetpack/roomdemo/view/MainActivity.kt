package com.jetpack.roomdemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jetpack.roomdemo.R
import com.jetpack.roomdemo.adapter.RecyclerViewAdapter
import com.jetpack.roomdemo.databinding.ActivityMainBinding
import com.jetpack.roomdemo.db.SubscriberDatabase
import com.jetpack.roomdemo.repository.SubscriberRepository
import com.jetpack.roomdemo.viewmodel.SubscriberViewModel
import com.jetpack.roomdemo.viewmodel.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private  val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory =  SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.viewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerview()

    }

    private fun initRecyclerview(){
      binding.rvSubscribers.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, { list ->
            binding.rvSubscribers.adapter = RecyclerViewAdapter(list)
        })
    }
}