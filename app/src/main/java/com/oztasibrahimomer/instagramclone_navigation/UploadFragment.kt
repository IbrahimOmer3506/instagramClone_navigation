package com.oztasibrahimomer.instagramclone_navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.oztasibrahimomer.instagramclone_navigation.R
import com.oztasibrahimomer.instagramclone_navigation.UploadFragmentDirections
import com.oztasibrahimomer.instagramclone_navigation.databinding.FragmentUploadBinding
import java.util.UUID

class UploadFragment : Fragment() {
    private lateinit var binding:FragmentUploadBinding

    private lateinit var storage:FirebaseStorage
    private lateinit var auth:FirebaseAuth
    private lateinit var fireStore:FirebaseFirestore



    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var uri: Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentUploadBinding.inflate(inflater,container,false)

        storage= Firebase.storage // storage initilaze edildi!

        auth=Firebase.auth
        fireStore=Firebase.firestore





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        register()
        binding.selectedIamge.setOnClickListener {
            selectImage(view)

        }
        binding.uploadButton.setOnClickListener {
            upload(view)
        }




    }
    fun selectImage(view: View){

        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            //require permission
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)){


                Snackbar.make(view,"Permission needed for gallery!!", Snackbar.LENGTH_INDEFINITE).setAction("give permission"){

                    // require permission
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)


                }.show()


            }
            else{
                //first require permission
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            }


        }
        else{
            //go to gallery!!
            val intentToGallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            activityResultLauncher.launch(intentToGallery)





        }

    }

    private fun register(){

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){result->

            if(result){
                // permission granted!!


                val intentToGallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                activityResultLauncher.launch(intentToGallery)


            }
            else{

                Toast.makeText(requireContext(),"permission denied!!",Toast.LENGTH_LONG).show()


            }
        }

        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

            if(result.resultCode== Activity.RESULT_OK){

                val intentFromGallery=result.data

                if(intentFromGallery!=null){

                    uri=intentFromGallery.data
                    try {
                        if(uri!=null){
                            binding.selectedIamge.setImageURI(uri)

                        }

                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }



                }
            }


        }
    }
    fun upload(view:View){

        val uuid=UUID.randomUUID() // random name oluşturacak!

        val referenceName= "images/${uuid}.jpg"

        // burada asenkron işlemleri yapacağım ve onviewCreated altında bnları aktif edecem!!!

        val storageReference=storage.reference.child(referenceName)

        if(uri!=null){

            println("messsi")

            storageReference.putFile(uri!!).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {uri->

                   println("ronaldo")

                   val postList= hashMapOf<String,Any>()

                   postList.put("downloadUrl",uri)
                   postList.put("comment",binding.commentText.text.toString())
                   postList.put("email",auth.currentUser!!.email.toString())
                   postList.put("date",com.google.firebase.Timestamp.now())

                   fireStore.collection("Posts").add(postList).addOnSuccessListener {

                       val action= UploadFragmentDirections.actionUploadFragmentToFeedFragment()

                       //val action=UploadFragmentDirections.actionUploadFragmentToFeedFragment()

                       Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(action)

                   }.addOnFailureListener {
                       Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                   }

                   }




            }.addOnFailureListener {

                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }




    }

}