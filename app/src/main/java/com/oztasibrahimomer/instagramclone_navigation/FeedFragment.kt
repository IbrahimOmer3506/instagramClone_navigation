package com.oztasibrahimomer.instagramclone_navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.oztasibrahimomer.instagramclone_navigation.PostAdapter
import com.oztasibrahimomer.instagramclone_navigation.Post
import com.oztasibrahimomer.instagramclone_navigation.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {
    private lateinit var binding:FragmentFeedBinding

    private lateinit var firestore: FirebaseFirestore

    private lateinit var postList:ArrayList<Post>

    var myAdapter: PostAdapter?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= FragmentFeedBinding.inflate(inflater,container,false)

        firestore= Firebase.firestore
        postList=ArrayList<Post>()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        getData()

        binding.feedRecyclerView.layoutManager=LinearLayoutManager(context)

        binding.feedRecyclerView.setHasFixedSize(true)

        myAdapter= PostAdapter(postList)

        binding.feedRecyclerView.adapter=myAdapter






    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){



        firestore.collection("Posts").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->


            if(error!=null){

                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }

            if(value!=null){
                if(!value.isEmpty){


                    println("messsiiiii")


                    postList.clear()
                    val documents=value.documents


                    for (doc in documents){
                        val email=doc.get("email") as String
                        val downloadurl=doc.get("downloadUrl") as String
                        val comment=doc.get("comment") as String

                        val date=doc.get("date") as Timestamp

                        val post= Post(email,downloadurl,comment,date)

                        postList.add(post)


                    }

                    myAdapter!!.notifyDataSetChanged()
                }



                println("ronaldooooo")


            }
        }

    }


}