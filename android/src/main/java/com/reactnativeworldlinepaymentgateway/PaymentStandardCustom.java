package com.reactnativeworldlinepaymentgateway;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.awl.merchanttoolkit.dto.ReqMsgDTO;
import com.awl.merchanttoolkit.transaction.AWLMEAPI;
import com.worldline.in.constant.Param;
import com.worldline.in.ipg.PaymentStandard;
import com.worldline.in.utility.Utility;

import java.io.IOException;

public class PaymentStandardCustom extends AppCompatActivity {

    private final String Tag = getClass().getSimpleName();
    private String encryptionKey;
    private String mid;
    private WebView webView;
    private FrameLayout webViewContainer;
    private ProgressDialog pd;


    private BroadcastReceiver receiver = new BroadcastReceiver() {

      @Override
      public void onReceive(Context context, Intent intent) {
        finish();
      }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_payment);

      /*Broadcast receiver implemented to finish activity when ipg is stuck but merchant app received notification*/
      IntentFilter filter = new IntentFilter();
      filter.addAction(getString(R.string.broadcast_action_name));
      registerReceiver(receiver, filter);
      pd = new ProgressDialog(this);
      pd.setMessage("Please wait Loading...");
      final ProgressBar progressBar = findViewById(R.id.ipg_progressBar);
      webViewContainer = findViewById(R.id.frame_layout);
      webView = findViewById(R.id.web_view);
      CookieManager cookieManager = CookieManager.getInstance();

      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        cookieManager.removeAllCookie();
      } else
        cookieManager.removeAllCookies(null);

      webView.clearCache(true);
      webView.clearHistory();

      webView.setWebViewClient(new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
          webView.postDelayed(new Runnable() {
            @Override
            public void run() {
              if (!pd.isShowing())
                pd.show();
              view.loadUrl(url);
            }
          }, 1000);
          return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
          Log.e("PaymentStandard","onReceivedError");
          if (pd.isShowing()) {
            pd.dismiss();
          }
          Toast.makeText(PaymentStandardCustom.this, "Error:" + description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
          // handler.proceed();
          //super.onReceivedSslError(view, handler, error);
          if (pd.isShowing()) {
            pd.dismiss();
          }
          final SslErrorHandler handler1 = handler;
          final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentStandardCustom.this);
          String message = "SSL Certificate error.";
          switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
              message = "Problem with security certificate for this site.";
              break;
            case SslError.SSL_EXPIRED:
              message = "The certificate has expired.";
              break;
            case SslError.SSL_IDMISMATCH:
              message = "The certificate Hostname mismatch.";
              break;
            case SslError.SSL_NOTYETVALID:
              message = "The certificate is not yet valid.";
              break;
          }
          message += " Do you want to continue anyway?";

          // builder.setTitle("SSL Certificate Error");
          builder.setMessage(message);
          builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              handler1.proceed();

            }
          });
          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

              handler1.cancel();

              finish();
            }
          });
          final AlertDialog dialog = builder.create();
          dialog.show();
        }

        @Override

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
          super.onPageStarted(view, url, favicon);
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onPageFinished(WebView view, String url) {
          if (pd.isShowing()) {
            pd.dismiss();
          }
          String pageTitle = view.getTitle();

          if (pageTitle.equals(getString(R.string.page_title))) {
            view.evaluateJavascript("(function(){return window.document.body.outerHTML})();",
              new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String html) {
                  String transactionResult = html.contains(getString(R.string.success)) ? "Success" : "Failed";

                  int startIndex = html.lastIndexOf("responseString") + 44;
                  int endIndex = html.lastIndexOf("|{");
                  String pipeSeparatedResponse = html.substring(startIndex, endIndex).trim();
                  String[] resultArray = pipeSeparatedResponse.split("\\|",-1);

                  Intent data = new Intent();
                  data.putExtra(Param.ORDER_ID, resultArray[0]);
                  data.putExtra(Param.TRANSACTION_REFERENCE_NUMBER, resultArray[1]);
                  data.putExtra(Param.RRN, resultArray[2]);
                  data.putExtra(Param.STATUS_CODE, resultArray[3]);
                  data.putExtra(Param.STATUS_DESCRIPTION, resultArray[4]);
                  data.putExtra(Param.TRANSACTION_AMOUNT, resultArray[5]);
                  data.putExtra(Param.TRANSACTION_REQUEST_DATE, resultArray[6]);
                  data.putExtra(Param.AUTH_N_STATUS, resultArray[7]);
                  data.putExtra(Param.AUTH_Z_STATUS, resultArray[8]);
                  data.putExtra(Param.CAPTURE_STATUS, resultArray[9]);
                  data.putExtra(Param.PG_REF_CANCEL_REQ_ID, resultArray[10]);
                  data.putExtra(Param.REFUND_AMOUNT, resultArray[11]);
                  data.putExtra(Param.ADDL_FIELD_1, resultArray[12]);
                  data.putExtra(Param.ADDL_FIELD_2, resultArray[13]);
                  data.putExtra(Param.ADDL_FIELD_3, resultArray[14]);
                  data.putExtra(Param.ADDL_FIELD_4, resultArray[15]);
                  data.putExtra(Param.ADDL_FIELD_5, resultArray[16]);
                  data.putExtra(Param.ADDL_FIELD_6, resultArray[17]);
                  data.putExtra(Param.ADDL_FIELD_7, resultArray[18]);
                  data.putExtra(Param.ADDL_FIELD_8, resultArray[19]);
                  data.putExtra(Param.ADDL_FIELD_9, resultArray[20]);
                  setResult(RESULT_OK, data);
                  webViewContainer.removeView(webView);
                  webView.removeAllViews();
                  webView.destroy();
                  finish();
                }
              });
          }
        }
      });

      WebSettings webSettings = webView.getSettings();
      webSettings.setJavaScriptEnabled(true);
      webSettings.setLoadWithOverviewMode(true);
      webSettings.setUseWideViewPort(true);

      try {

        Intent appIntent = getIntent();
        if (null != appIntent.getStringExtra(getString(R.string.key)) && null != appIntent.getStringExtra(getString(R.string.mid))) {
          encryptionKey = appIntent.getStringExtra(getString(R.string.key));
          mid = appIntent.getStringExtra(getString(R.string.mid));
        } else {
          encryptionKey = Utility.getMerchantDetails(getString(R.string.key), this);
          mid = Utility.getMerchantDetails(getString(R.string.mid), this);
        }

      } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
      }

      if (encryptionKey != null && mid != null) {

        Intent paymentParams = getIntent();
        String orderId = paymentParams.getStringExtra(Param.ORDER_ID);
        String transactionAmount = paymentParams.getStringExtra(Param.TRANSACTION_AMOUNT);
        String transactionCurrency = paymentParams.getStringExtra(Param.TRANSACTION_CURRENCY);
        String transactionDescription = paymentParams.getStringExtra(Param.TRANSACTION_DESCRIPTION);
        String transactionType = paymentParams.getStringExtra(Param.TRANSACTION_TYPE);
        String addField_2 = paymentParams.getStringExtra(Param.ADDL_FIELD_2);
        String addField_3 = paymentParams.getStringExtra(Param.ADDL_FIELD_3);
        String addField_4 = paymentParams.getStringExtra(Param.ADDL_FIELD_4);
        String addField_5 = paymentParams.getStringExtra(Param.ADDL_FIELD_5);
        String addField_6 = paymentParams.getStringExtra(Param.ADDL_FIELD_6);
        String addField_7 = paymentParams.getStringExtra(Param.ADDL_FIELD_7);
        String addField_8 = paymentParams.getStringExtra(Param.ADDL_FIELD_8);

        boolean isAllValid = validateParams(orderId, transactionAmount, transactionCurrency, transactionDescription, transactionType);
        if (isAllValid) {

          String url, responseURL;
          try {
            url = Utility.getProperty(getString(R.string.ipg_transaction_standard), getApplicationContext());
            responseURL = Utility.getProperty(getString(R.string.ipg_response_url), getApplicationContext());

            ReqMsgDTO objReqMsgDTO = new ReqMsgDTO();
            objReqMsgDTO.setOrderId(orderId);
            objReqMsgDTO.setMid(mid);
            objReqMsgDTO.setTrnAmt(transactionAmount);
            objReqMsgDTO.setTrnCurrency(transactionCurrency);
            objReqMsgDTO.setMeTransReqType(transactionType);
            objReqMsgDTO.setEnckey(encryptionKey);
            objReqMsgDTO.setResponseUrl(responseURL);
            objReqMsgDTO.setTrnRemarks(transactionDescription);
            objReqMsgDTO.setAddField1(this.getString(R.string.sdk_identifier));
            objReqMsgDTO.setAddField2(addField_2);
            objReqMsgDTO.setAddField3(addField_3);
            objReqMsgDTO.setAddField4(addField_4);
            objReqMsgDTO.setAddField5(addField_5);
            objReqMsgDTO.setAddField6(addField_6);
            objReqMsgDTO.setAddField7(addField_7);
            objReqMsgDTO.setAddField8(addField_8);

            AWLMEAPI objAWLMEAPI = new AWLMEAPI();
            try {
              objReqMsgDTO = objAWLMEAPI.generateTrnReqMsg(objReqMsgDTO);
              String merchantRequest;
              if (objReqMsgDTO.getStatusDesc().equals("Success")) {
                merchantRequest = objReqMsgDTO.getReqMsg();
                processPayment(merchantRequest, url, mid);
              } else
                Log.e(Tag, getString(R.string.error_transaction_request_message));

            } catch (Exception e) {
              e.printStackTrace();
            }


          } catch (IOException e) {
            Log.e(Tag, getString(R.string.error_missing_url));
            e.printStackTrace();
          }

        }else{
          Toast.makeText(PaymentStandardCustom.this, "Error: Invalid Details" , Toast.LENGTH_SHORT).show();
          this.finish();
        }
      } else {
        if (encryptionKey == null) {
//          Log.e(Tag, getString(R.string.error_encryption_key_not_found));
        } else {
//          Log.e(Tag, getString(R.string.error_mid_not_found));
        }
        Toast.makeText(PaymentStandardCustom.this, "Error: Invalid Details" , Toast.LENGTH_SHORT).show();
        this.finish();
      }

    }

    @Override
    protected void onDestroy() {
      super.onDestroy();
      unregisterReceiver(receiver);
    }

    private void processPayment(String merchantRequest, String url, String mid) {

      String requestHeader = "merchantRequest=" + merchantRequest + "&MID=" + mid;

      byte[] requestHeaderBytes = requestHeader.getBytes();
      if (!pd.isShowing())
        pd.show();
      webView.postUrl(url, requestHeaderBytes);

    }


    private boolean validateParams(String orderId, String transactionAmount, String transactionCurrency, String transactionDescription, String transactionType) {
      return validateParamsForStandardTransaction(orderId, transactionAmount, transactionCurrency, transactionDescription, transactionType);
    }

    private boolean validateParamsForStandardTransaction(String orderId, String transactionAmount, String transactionCurrency, String transactionDescription, String transactionType) {

      boolean isAllValid = true;

      if (null == orderId || orderId.equals("")) {
        Log.e(Tag, getString(R.string.error_missing_order_id));
        isAllValid = false;
      }
      if (null == transactionAmount || transactionAmount.equals("")) {
        Log.e(Tag, getString(R.string.error_missing_transaction_amount));
        isAllValid = false;
      }
      if (null == transactionDescription || transactionDescription.equals("")) {
        Log.e(Tag, getString(R.string.error_missing_transaction_discription));
        isAllValid = false;
      }
      if (null == transactionCurrency || transactionCurrency.equals("")) {
        Log.e(Tag, getString(R.string.error_missing_transaction_currency));
        isAllValid = false;
      }
      if (null == transactionDescription || transactionType.equals("")) {
        Log.e(Tag, getString(R.string.error_missing_transaction_type));
        isAllValid = false;
      }
      return isAllValid;

    }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    Intent intent = new Intent();
    setResult(Activity.RESULT_CANCELED, intent);
    super.onBackPressed();
  }
}
