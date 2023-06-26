package com.oztasibrahimomer.instagramclone_navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oztasibrahimomer.instagramclone_navigation.FeedFragmentDirections
import com.oztasibrahimomer.instagramclone_navigation.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAppBinding

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth=Firebase.auth

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater:MenuInflater=menuInflater

        inflater.inflate(R.menu.my_app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId== R.id.adding){
            val action= FeedFragmentDirections.actionFeedFragmentToUploadFragment()
            Navigation.findNavController(this, R.id.fragmentContainerView).navigate(action)




        }
        else if(item.itemId== R.id.logOut){

            auth.signOut()

            val intent= Intent(this@AppActivity, MainActivity::class.java)
            finish()
            startActivity(intent)


        }
        return super.onOptionsItemSelected(item)
    }


}