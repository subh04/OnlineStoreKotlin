package com.example.onlinestorekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_finalize_shopping.*

class FinalizeShopping : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        //http://192.168.0.3/OnlineStoreApp/calculate_total_price.php?invoice_num=

        var calculateTotalPriceURL="http://192.168.0.3/OnlineStoreApp/calculate_total_price.php?invoice_num="+intent.getStringExtra("LATEST_INVOICE_NUMBER")
        var requestQ=Volley.newRequestQueue(this@FinalizeShopping)
        var stringRequest=StringRequest(Request.Method.GET,calculateTotalPriceURL,Response.Listener { response ->

            btnPaymentProcessing.text="Pay Rs."+response+" via PayPal Now!"


        },Response.ErrorListener { error ->



        })

        requestQ.add(stringRequest)



    }
}
