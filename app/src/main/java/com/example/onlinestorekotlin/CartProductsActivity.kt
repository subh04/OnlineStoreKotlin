package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsURL="http://192.168.0.3/OnlineStoreApp/fetch_temporary_order.php?email="+Person.email
        var cartProductsList=ArrayList<String>()
        var requestQ= Volley.newRequestQueue(this@CartProductsActivity)
        var jsonArrayRequest=JsonArrayRequest(Request.Method.GET,cartProductsURL,null,Response.Listener { response ->


            for (jsonObjectIndex in 0.until(response.length())){
                cartProductsList.add("${response.getJSONObject(jsonObjectIndex).getInt("id")} \n ${response.getJSONObject(
                    jsonObjectIndex
                ).getString("name")} \n ${response.getJSONObject(jsonObjectIndex).getInt("price")} \n ${response.getJSONObject(
                    jsonObjectIndex
                ).getString("email")} \n ${response.getJSONObject(jsonObjectIndex).getInt("amount")} \n")



            }
            var cartProductsAdapter=ArrayAdapter(this@CartProductsActivity,android.R.layout.simple_list_item_1,cartProductsList)
            cartProductsListView.adapter=cartProductsAdapter





        },Response.ErrorListener { error ->




        })
        requestQ.add(jsonArrayRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item?.itemId==R.id.continueShoppingItem){
            var intent=Intent(this,HomeScreen::class.java)
            startActivity(intent)
        }
        else if (item?.itemId==R.id.declineOrderItem){

            var declineCartURL="http://192.168.0.3/OnlineStoreApp/decline_orders.php?email="+Person.email
            var requestQ=Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest=StringRequest(Request.Method.GET,declineCartURL,Response.Listener { response ->

                var intent=Intent(this,HomeScreen::class.java)
                startActivity(intent)



            },Response.ErrorListener { error ->



            })
            requestQ.add(stringRequest)

        }
        return super.onOptionsItemSelected(item)
    }
}
