package com.oztasibrahimomer.instagramclone_navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.oztasibrahimomer.instagramclone_navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth= Firebase.auth // auth initilaze!!!


        val currentUser=auth.currentUser

        if(currentUser!=null){
            val intent = Intent(this@MainActivity, AppActivity::class.java)
            finish()
            startActivity(intent)


        }

        binding.progressBar.visibility=View.INVISIBLE
        binding.imageView2.visibility=View.VISIBLE



    }
    fun signIn(view: View){
        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()

        if(email.equals("") || password.equals("")){
            Toast.makeText(this@MainActivity,"adam akilli Enter email or password lan",Toast.LENGTH_LONG).show()

        }
        else{

            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val timer = object: CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.progressBar.visibility=View.VISIBLE
                        binding.imageView2.visibility=View.INVISIBLE


                    }

                    override fun onFinish() {

                        val intent = Intent(this@MainActivity, AppActivity::class.java)
                        finish()
                        startActivity(intent)

                    }
                }.start()


            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }



    }
    fun signUp(view:View){

        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()


        if(email.equals("")|| password.equals("")){

            Toast.makeText(this@MainActivity,"adam akilli Enter email or password lan",Toast.LENGTH_LONG).show()

        }

        else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {

                val timer = object: CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.progressBar.visibility=View.VISIBLE
                        binding.imageView2.visibility=View.INVISIBLE


                    }

                    override fun onFinish() {
                        val intent = Intent(this@MainActivity, AppActivity::class.java)

                        finish()
                        startActivity(intent)

                    }
                }.start()

            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }


    }
}