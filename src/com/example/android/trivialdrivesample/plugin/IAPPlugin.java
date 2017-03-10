package com.example.android.trivialdrivesample.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabHelper.OnConsumeFinishedListener;
import com.example.android.trivialdrivesample.util.IabHelper.OnConsumeMultiFinishedListener;
import com.example.android.trivialdrivesample.util.IabHelper.OnIabPurchaseFinishedListener;
import com.example.android.trivialdrivesample.util.IabHelper.OnIabSetupFinishedListener;
import com.example.android.trivialdrivesample.util.IabHelper.QueryInventoryFinishedListener;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;

public class IAPPlugin extends IAPPluginBase implements OnIabPurchaseFinishedListener,QueryInventoryFinishedListener,OnConsumeFinishedListener,OnConsumeMultiFinishedListener{
	/**
	 * 
	 * @param key 后台key
	 */
	public List<Purchase> purchases;
	public IabHelper iHelper;
	private static IAPPlugin instance;
	public static IAPPlugin getInstance(){
		if(instance == null)
			instance = new IAPPlugin();
		return instance;
	}
	public void init(String key){
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
	
	/**
	 * 根据 sku购买商品
	 * @param sku
	 */
	public void lauchPurchase(String sku){
		lauchPurchase(sku,null);
	}
	public void lauchPurchase(String sku,String extraData){
		lauchPurchase(sku, Constant.ITEM_TYPE_INAPP, extraData);
	}
	public void launchSubscriptionPurchase(String sku){
		launchSubscriptionPurchase(sku,null);
	}
	public void launchSubscriptionPurchase(String sku,String extraData){
		lauchPurchase(sku, Constant.ITEM_TYPE_SUBS, extraData);
	}
	
	/**
	 * 获取后台商品列表 包括已经拥有的商品
	 * @param skus
	 */
	public void queryInventory(String[] skus){
		iHelper.queryInventoryAsync(true,Arrays.asList(skus),IAPPlugin.this);
	}
	
	/**
	 * 消耗商品
	 * @param sku
	 */
	public void consumeProduct(String sku){
		Purchase purchase = getPurchase(sku);
		if(purchase!=null)
			iHelper.consumeAsync(purchase, IAPPlugin.this);
	}
	
	/**
	 * 消耗商品
	 * @param sku
	 */
	public void consumeProducts(String[] sku){
		List<Purchase> skuList = new ArrayList<Purchase>();
		for (int i = 0; i < sku.length; i++) {
			String string = sku[i];
			Purchase purchase = getPurchase(string);
			if(purchase!=null)
				skuList.add(purchase);
		}
		iHelper.consumeAsync(skuList, IAPPlugin.this);
	}
	
	private Purchase getPurchase(String sku){
		for (int i = 0; i < purchases.size(); i++) {
			Purchase purchase = purchases.get(i);
			if(purchase.getSku().equals(sku)){
				return purchase;
			}
		}
		return null;
	}
	
	public void consumeProducts(){
		iHelper.consumeAsync(purchases, IAPPlugin.this);
	}
	
	private void lauchPurchase(String sku,String itemType,String extraData){
		Activity activity = getActivity();
		if(activity == null)
			return;
		Intent intent = new Intent(activity, IAPActivity.class);
	//	Bundle bundle = new Bundle();
		intent.putExtra(Constant.ITEM_TYPE,itemType);
		intent.putExtra(Constant.SKU, sku);
		intent.putExtra(Constant.EXTRA_DATA,extraData);
	//	intent.putExtra("bund", value)
		activity.startActivity(intent);
	}
	
	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase info) {
		// TODO Auto-generated method stub
		if(result.isSuccess()){
			Log.d(Constant.Tag, "onIabPurchaseFinished success");
			purchases.add(info);
		}else if (result.isFailure()) {
			Log.d(Constant.Tag, "onIabPurchaseFinished fail "+result.getMessage());
		}
	}
	
	@Override
	public void onQueryInventoryFinished(IabResult result, Inventory inv) {
		// TODO Auto-generated method stub
		if(result.isSuccess()){
			Log.d(Constant.Tag, "onQueryInventoryFinished success");
			Log.d(Constant.Tag, "purchase->"+inv.getAllPurchaseJson().toString());
			Log.d(Constant.Tag, "skus->"+inv.getAllOwnedSkusJson().toString());
		}else if (result.isFailure()) {
			Log.d(Constant.Tag, "onQueryInventoryFinished fail "+result.getMessage());
		}
	}

	@Override
	public void onConsumeMultiFinished(List<Purchase> purchases,
			List<IabResult> results) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConsumeFinished(Purchase purchase, IabResult result) {
		// TODO Auto-generated method stub
		
		if(result.isSuccess()){
			Log.d(Constant.Tag, "onConsumeFinished success");
		}else if (result.isFailure()) {
			Log.d(Constant.Tag, "onConsumeFinished fail "+result.getMessage());
		}
	}
	
	
}
