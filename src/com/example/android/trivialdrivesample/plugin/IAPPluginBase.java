package com.example.android.trivialdrivesample.plugin;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;

import android.app.Activity;

public class IAPPluginBase {
	private static WeakReference<Activity> refAct; 
	public static Activity getActivity(){
		Activity activity = null;
		if(refAct!=null){
			activity = refAct.get();
			if(activity != null)
				return activity;
		}
		if(activity == null){
			try {
		        Class activityThreadClass = Class.forName("android.app.ActivityThread");
		        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
		        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
		        activitiesField.setAccessible(true);
		        Map activities = null;
//		        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // 4.4 以下使用的是 HashMap
//		            activities = (HashMap) activitiesField.get(activityThread);
//		        }else{ // 4.4 以上使用的是 ArrayMap
		        activities = (Map) activitiesField.get(activityThread);
//		        }
		        for (Object activityRecord : activities.values()) {
		            Class activityRecordClass = activityRecord.getClass();
		            Field pausedField = activityRecordClass.getDeclaredField("paused"); // 找到 paused 为 false 的activity
		            pausedField.setAccessible(true);
		            if (!pausedField.getBoolean(activityRecord)) {
		                Field activityField = activityRecordClass.getDeclaredField("activity");
		                activityField.setAccessible(true);
		                activity = (Activity) activityField.get(activityRecord);
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }   
		}
		return activity;
	}
}
