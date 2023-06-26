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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.oztasibrahimomer.instagramclone_navigation.MessageAdapter
import com.oztasibrahimomer.instagramclone_navigation.Message
import com.oztasibrahimomer.instagramclone_navigation.databinding.FragmentMessagesBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MessagesFragment : Fragment() {
    private lateinit var binding: FragmentMessagesBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var messagaList: ArrayList<Message>

    var myAdapter: MessageAdapter? = null

    var uuid: UUID? = null
    var docName: String? = null

    var date:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMessagesBinding.inflate(inflater, container, false)

        firestore = Firebase.firestore
        auth = Firebase.auth

        messagaList = ArrayList<Message>()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

             date=MessagesFragmentArgs.fromBundle(it).date
        }



        binding.sendIcon.setOnClickListener {
            sendMessage(it)
        }

        //getData()


        binding.MessageRecyclerView.layoutManager = LinearLayoutManager(context)
        myAdapter = MessageAdapter(messagaList)

        binding.MessageRecyclerView.adapter = myAdapter


    }

    @SuppressLint("NotifyDataSetChanged")
    fun sendMessage(view: View) {



        val message = binding.messageEditText.text.toString()

        val userEmail = auth.currentUser!!.email

        val hashMap = HashMap<String, Any>()

        hashMap.put("message", message)
        hashMap.put("userEmail", userEmail!!)

        hashMap.put("newDate",Timestamp.now())
        val r=UUID.randomUUID()

       // val degisken="${r}"


        if(!message.equals("")){

            val messageCollection=firestore.collection(date!!)


            messageCollection.add(hashMap).addOnSuccessListener {

                binding.messageEditText.text.clear()



               messageCollection.orderBy("newDate",Query.Direction.DESCENDING).addSnapshotListener { value, error ->


                   if(error!=null){

                       Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
                   }

                   if(value!=null && !value.isEmpty){

                       messagaList.clear()

                       val documents =value.documents

                       for(document in documents){

                           val gelenMessage=document.get("message") as String
                           val gelenEmail=document.get("userEmail") as String

                           val mesaj=Message(gelenMessage,gelenEmail)

                           messagaList.add(mesaj)
                       }


                       myAdapter!!.notifyDataSetChanged()

                   }
               }






            }.addOnFailureListener {

                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }









                /*messageCollection.ad(hashMap).addOnSuccessListener {


                    binding.messageEditText.text.clear()






                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }*/


            }






    }

    //@SuppressLint("NotifyDataSetChanged")
   /* private fun getData() {

        val reference = firestore.collection("Message").document(docName!!)
        // firebase den documanları alacağız!!!

        println("1111111111")

        /*firestore.collection("Message").document(docName!!).addSnapshotListener { value, error ->
            println("333333333")

            if(error!=null){

                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()
            }

            if(value!=null && value.exists()){

                messagaList.clear()

                val gelenData=value.data



                for (doc in gelenData!!){

                    doc.






                    val gelenEmail=doc.get("userEmail") as String
                    val gelenMessage=doc.get("message") as String

                    val messageClass=Message(gelenMessage,gelenEmail)

                    messagaList.add(messageClass)*/

        reference.get().addOnSuccessListener {
            println("naime")
            if (it.exists()) {
                val data = it.data

                val gelenEmail = data?.get("userEmail") as String

                val gelenMessage = data.get("message") as String

                val message = Message(gelenEmail, gelenMessage)

                messagaList.add(message)
            }


        }

        myAdapter!!.notifyDataSetChanged()
        println("ibrahim")


    }*/





}


