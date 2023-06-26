package com.oztasibrahimomer.instagramclone_navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oztasibrahimomer.instagramclone_navigation.databinding.RecyclerRowBinding
import com.squareup.picasso.Picasso

class PostAdapter(val postList:ArrayList<Post>): RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.binding.recyclerEmailText.text= postList[position].email

        holder.binding.recyclerCommentText.text= postList[position].comment
        Picasso.get().load(postList[position].downloadUrl).into(holder.binding.recyclerImage)

        holder.binding.recyclerChatIcon.setOnClickListener {

            val action = FeedFragmentDirections.actionFeedFragmentToMessagesFragment(postList[position].date.toString())


            Navigation.findNavController(it).navigate(action)


        }


    }


}