package com.example.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //http://192.168.0.6/OnlineStoreApp/fetch_brands.php

        var brandsURL="http://192.168.0.6/OnlineStoreApp/fetch_brands.php"
        var brandsList=ArrayList<String>()
        var requestQ=Volley.newRequestQueue(this@HomeScreen)
        var jsonArrReq= JsonArrayRequest(Request.Method.GET,brandsURL,null,Response.Listener { response ->

            for (jsonObject in 0.until(response.length())){
                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))

            }
            var brandsListAdapter=ArrayAdapter(this@HomeScreen,android.R.layout.simple_list_item_1,brandsList)
            brandsListView.adapter=brandsListAdapter





        },Response.ErrorListener { error ->

            val dialogueBuilder= AlertDialog.Builder(this)
            dialogueBuilder.setTitle("Error Message")
            dialogueBuilder.setMessage(error.message)
            dialogueBuilder.create().show()



        })

        requestQ.add(jsonArrReq)




    }
}
