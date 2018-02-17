package com.example.intlcall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class MiscHelper {
	
	Context context = null;
	
	public MiscHelper(Context context){
		this.context = context;
	}
	
	
	public String callNum(CharSequence phoneNum){
		String cleanNum;
		Log.i("MakeNum ", phoneNum.toString());
		cleanNum = phoneNum.toString();
		cleanNum = cleanNum.replaceAll("[-() ]", "");
		if (cleanNum.length() >= 10){
			cleanNum = cleanNum.substring(cleanNum.length() - 10, cleanNum.length());
			cleanNum = "01191" + cleanNum;
			cleanNum = "9175295500,,515444,,," + cleanNum;
		}
	    if (cleanNum.length() >= 10){
		    Intent callIntent = new Intent(Intent.ACTION_CALL);
	        //callIntent.setData(Uri.parse("tel:" + Uri.encode(cleanNum.trim())));
		    callIntent.setData(Uri.parse("tel:" + cleanNum.trim()));
	        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(callIntent);   
	    }
	    else {
	    	Toast toast = Toast.makeText(context, "Phone Number less than 10 characters", Toast.LENGTH_SHORT);
	    	toast.show();
	    }
		return cleanNum;
	}

}
