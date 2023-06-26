package com.oztasibrahimomer.instagramclone_navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oztasibrahimomer.instagramclone_navigation.databinding.RecyclerMessageRowBinding

class MessageAdapter(val messageList:ArrayList<Message>):
    RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    class MessageHolder(val binding:RecyclerMessageRowBinding):RecyclerView.ViewHolder(binding.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val binding=RecyclerMessageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MessageHolder(binding)


    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {

        holder.binding.mesId.text=messageList[position].message
        holder.binding.mesEmailId.text=messageList[position].email

    }
}