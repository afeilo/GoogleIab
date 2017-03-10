package com.example.android.trivialdrivesample.plugin;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabHelper.OnIabPurchaseFinishedListener;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class IAPActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String sku = savedInstanceState.getString(Constant.SKU);
		String item_type = savedInstanceState.getString(Constant.ITEM_TYPE);
		String extra_data = savedInstanceState.getString(Constant.EXTRA_DATA);
		IabHelper iHelper = IAPPlugin.iHelper;
		if(iHelper == null)
			finish();
		iHelper.launchPurchaseFlow(this, sku, item_type, 100, new OnIabPurchaseFinishedListener() {

			@Override
			public void onIabPurchaseFinished(IabResult result, Purchase info) {
				// TODO Auto-generated method stub
				if(result.isSuccess()){
					Log.d(Constant.Tag, "onIabPurchaseFinished success");
				}else if (result.isFailure()) {
					Log.d(Constant.Tag, "onIabPurchaseFinished fail "+result.getMessage());
				}
			}
		}, extra_data);
	}
}
