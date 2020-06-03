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
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignUp_SignUp.setOnClickListener {
            if(signUpUserPassword1.text.toString().equals(signUpUserPasswordConfirm.text.toString())){

                //Registration process http://192.168.2.102/OnlineStoreApp/join_new_users.php
                val signUpURL="http://192.168.0.3/OnlineStoreApp/join_new_users.php?email="+
                        signUpEmail.text.toString()+
                        "&username="+signUpUserName.text.toString()+
                        "&pass="+signUpUserPasswordConfirm.text.toString()
                val requestQ=Volley.newRequestQueue(this@SignUpActivity)
                val stringRequest=StringRequest(Request.Method.GET,signUpURL,Response.Listener {response ->


                    if (response.equals("A user with same email already exists")){

                        val dialogueBuilder=AlertDialog.Builder(this)
                        dialogueBuilder.setTitle("Error Message")
                        dialogueBuilder.setMessage(response)
                        dialogueBuilder.create().show()


                    }else{
//                        val dialogueBuilder=AlertDialog.Builder(this)
//                        dialogueBuilder.setTitle("Congratulations")
//                        dialogueBuilder.setMessage(response)
//                        dialogueBuilder.create().show()
                        Person.email=signUpEmail.text.toString()
                        Toast.makeText(this@SignUpActivity,response,Toast.LENGTH_SHORT).show()
                        val homeIntent=Intent(this@SignUpActivity,HomeScreen::class.java)
                        startActivity(homeIntent)

                    }


                },Response.ErrorListener {error ->


                    val dialogueBuilder=AlertDialog.Builder(this)
                    dialogueBuilder.setTitle("Error Message")
                    dialogueBuilder.setMessage(error.message)
                    dialogueBuilder.create().show()
                    
                    
                    
                })
                requestQ.add(stringRequest)

            }else{
                //Toast.makeText(applicationContext,"Both the passwords do not match",Toast.LENGTH_SHORT).show()
                val dialogueBuilder=AlertDialog.Builder(this)
                dialogueBuilder.setTitle("Error Message")
                dialogueBuilder.setMessage("Password Mismatch")
                dialogueBuilder.create().show()
            }
        }
        btnSignUp_Login.setOnClickListener {
            finish()
        }
    }
}
