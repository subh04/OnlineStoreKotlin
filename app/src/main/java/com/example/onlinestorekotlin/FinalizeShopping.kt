package com.example.onlinestorekotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import java.math.BigDecimal

class FinalizeShopping : AppCompatActivity() {

    var totalPrice:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        //http://192.168.0.3/OnlineStoreApp/calculate_total_price.php?invoice_num=

        var calculateTotalPriceURL="http://192.168.0.3/OnlineStoreApp/calculate_total_price.php?invoice_num="+intent.getStringExtra("LATEST_INVOICE_NUMBER")
        var requestQ=Volley.newRequestQueue(this@FinalizeShopping)
        var stringRequest=StringRequest(Request.Method.GET,calculateTotalPriceURL,Response.Listener { response ->

            btnPaymentProcessing.text="Pay $."+response+" via PayPal Now!"
            totalPrice=response.toLong()


        },Response.ErrorListener { error ->



        })

        requestQ.add(stringRequest)


        var paypalConfig:PayPalConfiguration=PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(MyPayPal.clientID)
        var ppService = Intent(this@FinalizeShopping,PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfig)
        startService(ppService)

        btnPaymentProcessing.setOnClickListener {

            var ppProcessing=PayPalPayment(BigDecimal.valueOf(totalPrice),
                "USD","Online E commerce App Kotlin ",
                PayPalPayment.PAYMENT_INTENT_SALE)

            var paypalPaymentIntent=Intent(this,PaymentActivity::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT,ppProcessing)
            startActivityForResult(paypalPaymentIntent,1000)



        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1000){
            if (resultCode==Activity.RESULT_OK){
                var intent=Intent(this,ThankyouActivity::class.java)
                startActivity(intent)
            }else{

                Toast.makeText(this,"Sorry! something went wrong. Try again",Toast.LENGTH_SHORT).show()


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this,PayPalService::class.java))
    }
}
