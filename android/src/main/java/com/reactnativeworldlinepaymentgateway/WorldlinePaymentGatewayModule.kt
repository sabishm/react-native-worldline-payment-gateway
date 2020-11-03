package com.reactnativeworldlinepaymentgateway

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat.startActivityForResult
import com.facebook.react.bridge.*
import com.worldline.`in`.constant.Param
import org.json.JSONObject


class WorldlinePaymentGatewayModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener {

    private val paymentRequestCode = 21001
    private lateinit var transactionCallBack:Promise
    private  var key: String? = null
    private  var mid: String? = null

    init {
        reactContext.addActivityEventListener(this)
    }

    override fun getName(): String {
        return "WorldlinePaymentGateway"
    }

    @ReactMethod
    fun startPayment(map: ReadableMap, promise: Promise) {
      this.transactionCallBack = promise
      val intent = Intent(reactApplicationContext, PaymentStandardCustom::class.java)

      intent.putExtra(Param.ORDER_ID, map.getString("order_id"))
      intent.putExtra(Param.TRANSACTION_AMOUNT, map.getString("amount"))
      intent.putExtra(Param.TRANSACTION_CURRENCY, map.getString("currency"))
      intent.putExtra(Param.TRANSACTION_DESCRIPTION, map.getString("description"))
      intent.putExtra(Param.TRANSACTION_TYPE, map.getString("transaction_type"))
      intent.putExtra(Param.KEY, map.getString("key"))
      intent.putExtra(Param.MID, map.getString("mid"))
      startActivityForResult(currentActivity!!, intent, paymentRequestCode, null)
    }

  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == paymentRequestCode) {
      if (resultCode == RESULT_OK) {
        val orderId = data?.getStringExtra(Param.ORDER_ID)
        val transactionRefNo = data?.getStringExtra(Param.TRANSACTION_REFERENCE_NUMBER)
        val rrn = data?.getStringExtra(Param.RRN)
        val statusCode = data?.getStringExtra(Param.STATUS_CODE)
        val statusDescription = data?.getStringExtra(Param.STATUS_DESCRIPTION)
        val transactionAmount = data?.getStringExtra(Param.TRANSACTION_AMOUNT)
        val requestDate = data?.getStringExtra(Param.TRANSACTION_REQUEST_DATE)
        val authNStatus = data?.getStringExtra(Param.AUTH_N_STATUS)
        val authZstatus = data?.getStringExtra(Param.AUTH_Z_STATUS)
        val captureStatus = data?.getStringExtra(Param.CAPTURE_STATUS)
        val pgRefCancelReqId = data?.getStringExtra(Param.PG_REF_CANCEL_REQ_ID)
        val refundAmount = data?.getStringExtra(Param.REFUND_AMOUNT)
        val addField1 = data?.getStringExtra(Param.ADDL_FIELD_1)
        val addField2 = data?.getStringExtra(Param.ADDL_FIELD_2)
        val addField3 = data?.getStringExtra(Param.ADDL_FIELD_3)
        val addField4 = data?.getStringExtra(Param.ADDL_FIELD_4)
        val addField5 = data?.getStringExtra(Param.ADDL_FIELD_5)
        val addField6 = data?.getStringExtra(Param.ADDL_FIELD_6)
        val addField7 = data?.getStringExtra(Param.ADDL_FIELD_7)
        val addField8 = data?.getStringExtra(Param.ADDL_FIELD_8)
        val addField9 = data?.getStringExtra(Param.ADDL_FIELD_9)
        val addField10 = data?.getStringExtra(Param.ADDL_FIELD_10)

        val json = JSONObject()
        json.put("orderId",orderId)
        json.put("transactionRefNo",transactionRefNo)
        json.put("rrn",rrn)
        json.put("statusCode",statusCode)
        json.put("statusDescription",statusDescription)
        json.put("transactionAmount",transactionAmount)
        json.put("requestDate",requestDate)
        json.put("authNStatus",authNStatus)
        json.put("authZstatus",authZstatus)
        json.put("captureStatus",captureStatus)
        json.put("pgRefCancelReqId",pgRefCancelReqId)
        json.put("refundAmount",refundAmount)
        json.put("addField1",addField1)
        json.put("addField2",addField2)
        json.put("addField3",addField3)
        json.put("addField4",addField4)
        json.put("addField5",addField5)
        json.put("addField6",addField6)
        json.put("addField7",addField7)
        json.put("addField8",addField8)
        json.put("addField9",addField9)
        json.put("addField10",addField10)

        println(json.toString())

        transactionCallBack.resolve(json.toString())
      } else if(resultCode == RESULT_CANCELED){
        transactionCallBack.resolve("User Cancelled!")
      }
    }
  }

  override fun onNewIntent(intent: Intent?) {

  }


}
