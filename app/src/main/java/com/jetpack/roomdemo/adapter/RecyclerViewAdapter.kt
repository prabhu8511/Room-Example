package com.jetpack.roomdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jetpack.roomdemo.R
import com.jetpack.roomdemo.databinding.ListItemBinding
import com.jetpack.roomdemo.db.Subscriber

class RecyclerViewAdapter( private val itemClick: (Subscriber) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribers[position], itemClick)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    fun setList(subscribers:List<Subscriber>){
        this.subscribers.clear()
        this.subscribers.addAll(subscribers)
    }


    class MyViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subscriber: Subscriber, itemClick: (Subscriber) -> Unit) {
            binding.nameText.text = subscriber.name
            binding.emailText.text = subscriber.email
            binding.listItemLayout.setOnClickListener {
                itemClick(subscriber)
            }
        }
    }


}

