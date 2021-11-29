package com.jetpack.roomdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jetpack.roomdemo.R
import com.jetpack.roomdemo.databinding.ListItemBinding
import com.jetpack.roomdemo.db.Subscriber

class RecyclerViewAdapter(private val subscribers: List<Subscriber>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position])
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }


    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subscriber: Subscriber) {
            binding.nameText.text = subscriber.name
            binding.emailText.text = subscriber.email
        }
    }


}

