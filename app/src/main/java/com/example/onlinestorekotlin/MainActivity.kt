package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_btnSignup.setOnClickListener {
            var signUpIntent=Intent(this@MainActivity,SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
        main_btnLogin.setOnClickListener {
            //http://192.168.2.102/OnlineStoreApp/login_app_user.php
            val loginURL="http://192.168.0.6/OnlineStoreApp/login_app_user.php?email="+
                    edtLoginEmail_main.text.toString()+
                    "&pass="+edtLoginPassword_main.text.toString()
            val requestQ=Volley.newRequestQueue(this@MainActivity)
            val stringRequest=StringRequest(Request.Method.GET,loginURL,Response.Listener { response ->

                if (response.equals("user does exist")){

                    //Toast.makeText(this@MainActivity,response,Toast.LENGTH_SHORT).show()
                    Person.email=edtLoginEmail_main.text.toString()
                    val homeIntent=Intent(this@MainActivity,HomeScreen::class.java)
                    startActivity(homeIntent)


                }else{

                    val dialogueBuilder= AlertDialog.Builder(this)
                    dialogueBuilder.setTitle("Unknown User")
                    dialogueBuilder.setMessage(response)
                    dialogueBuilder.create().show()

                }







            },Response.ErrorListener { error ->

                val dialogueBuilder= AlertDialog.Builder(this)
                dialogueBuilder.setTitle("Error Message")
                dialogueBuilder.setMessage(error.message)
                dialogueBuilder.create().show()




            })
            requestQ.add(stringRequest)


        }
    }
}
