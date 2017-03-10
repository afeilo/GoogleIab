package com.example.android.trivialdrivesample.plugin;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabHelper.OnIabPurchaseFinishedListener;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class IAPActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		String sku = getIntent().getExtras().getString(Constant.SKU);
		String item_type = getIntent().getExtras().getString(Constant.ITEM_TYPE);
		String extra_data = getIntent().getExtras().getString(Constant.EXTRA_DATA);
		IabHelper iHelper = IAPPlugin.getInstance().iHelper;
		if(iHelper == null)
			finish();
		iHelper.launchPurchaseFlow(IAPActivity.this, sku, item_type, 100,new OnIabPurchaseFinishedListener() {
			
			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) {
				// TODO Auto-generated method stub
				IAPPlugin.getInstance().consumeProduct(info.getSku());
			}
		}, extra_data);
	}
	  public void onActivityResult(int requestCode, int resultCode, Intent data)
	  {
	    
//
//	    if (GoogleIABPlugin.instance().helper == null)
//	    {
//	      Log.e("Prime31-Proxy", "FATAL ERROR: Plugin singleton helper is null in onActivityResult. Attempting to abort operation to avoid a crash.");
//	      super.onActivityResult(requestCode, resultCode, data);
//	      finish();
//	      return;
//	    }
//
//	    if (!GoogleIABPlugin.instance().helper.handleActivityResult(requestCode, resultCode, data))
//	    {
//	      super.onActivityResult(requestCode, resultCode, data);
//	    }
//	    else
//	    {
//	      Log.d("Prime31-Proxy", "onActivityResult handled by IABUtil. All done here.");
//	    }
//
//	    finish();
	  }
}
