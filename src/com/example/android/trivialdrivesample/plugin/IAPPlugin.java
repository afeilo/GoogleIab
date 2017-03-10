package com.example.android.trivialdrivesample.plugin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabHelper.OnIabPurchaseFinishedListener;
import com.example.android.trivialdrivesample.util.IabHelper.OnIabSetupFinishedListener;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Purchase;

public class IAPPlugin extends IAPPluginBase implements OnIabPurchaseFinishedListener{
	/**
	 * 
	 * @param key ºóÌ¨key
	 */
	public static List<Purchase> purchases;
	public static IabHelper iHelper;
	public static void init(String key){
		if(key == null)
			return;
		Activity activity = getActivity();
		if(activity == null)
			return;
		purchases = new ArrayList<Purchase>();
		iHelper = new IabHelper(activity.getApplicationContext(),key);
		iHelper.startSetup(new OnIabSetupFinishedListener() {
			
			@Override
			public void onIabSetupFinished(IabResult result) {
				// TODO Auto-generated method stub
				if(result.isSuccess()){
					Log.d(Constant.Tag, "onIabSetupFinished success");
				}else if(result.isFailure()){
					Log.d(Constant.Tag, "onIabSetupFinished fail "+result.getMessage());
				}
			}
		});
	}
	public static void lauchPurchase(String sku){
		lauchPurchase(sku,null);
	}
	public static void lauchPurchase(String sku,String extraData){
		lauchPurchase(sku, Constant.ITEM_TYPE_INAPP, extraData);
	}
	public static void launchSubscriptionPurchase(String sku){
		launchSubscriptionPurchase(sku,null);
	}
	public static void launchSubscriptionPurchase(String sku,String extraData){
		lauchPurchase(sku, Constant.ITEM_TYPE_SUBS, extraData);
	}
	
	private static void lauchPurchase(String sku,String itemType,String extraData){
		Activity activity = getActivity();
		if(activity == null)
			return;
		Intent intent = new Intent(activity, IAPActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(Constant.ITEM_TYPE,itemType);
		bundle.putString(Constant.SKU, sku);
		bundle.putString(Constant.EXTRA_DATA,extraData);
		activity.startActivity(intent);
	}
	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		// TODO Auto-generated method stub
		
	}
	
	
}
