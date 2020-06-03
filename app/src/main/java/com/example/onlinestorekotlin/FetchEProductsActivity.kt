package com.example.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_e_products.*

class FetchEProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_e_products)

        val selectedBrand:String=intent.getStringExtra("BRAND")//here we get the value of the selected brand
        txtBrandName.text="Showing Products of "+selectedBrand
        var productsList=ArrayList<EProduct>()
        val productsURL="http://192.168.0.3/OnlineStoreApp/fetch_eproducts.php?brand="+selectedBrand
        val requestQ=Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonArrReq=JsonArrayRequest(Request.Method.GET,productsURL,null,Response.Listener { response ->

            for (productJsonObj in 0.until(response.length())){

                productsList.add(EProduct(response.getJSONObject(productJsonObj).getInt("id"),
                    response.getJSONObject(productJsonObj).getString("name"),
                    response.getJSONObject(productJsonObj).getInt("price"),
                    response.getJSONObject(productJsonObj).getString("picture")))



            }
            val PAdapter=EProductAdapter(this@FetchEProductsActivity,productsList)
            productsRecyclerView.layoutManager= LinearLayoutManager(this@FetchEProductsActivity)
            productsRecyclerView.adapter=PAdapter




            
            
            
        },Response.ErrorListener { error ->

            val dialogueBuilder= AlertDialog.Builder(this)
            dialogueBuilder.setTitle("Error Message")
            dialogueBuilder.setMessage(error.message)
            dialogueBuilder.create().show()


        })
        requestQ.add(jsonArrReq)



    }
}
