package com.example.intlcall;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	EditText etSearchbox;
	ListView lvFirst;
	ArrayAdapter<String> adapter1;
//S	String[] data = {"mehul joisar","amit mishra","amitabh","Aamir khan","jesica","katrina"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Log.i("On Create", " Created ........");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etSearchbox=(EditText)findViewById(R.id.editText1); 
		//etSearchbox = (EditText) new EditText(getBaseContext());
		lvFirst=(ListView)findViewById(R.id.contactList); 
		lvFirst.setTextFilterEnabled(true);
		
		/*adapter1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);*/
		//Log.i("On Create", "Calling readContactData");
		final LoadListAdapter adapter1 = readContactData();
		//Log.i("On Create", "Returned from readContactData");
		lvFirst.setAdapter(adapter1);

		etSearchbox.addTextChangedListener(new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		        // TODO Auto-generated method stub
		    	Log.i("In MainActivity", arg0.toString());
		    	adapter1.filter(arg0);
		    }

		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) {
		        // TODO Auto-generated method stub

		    }

		    @Override
		    public void afterTextChanged(Editable arg0) {
		        // TODO Auto-generated method stub

		    }
		});
		
		/****
		Button button = (Button) findViewById(R.id.add_contact);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				startActivity(intent);
				
			}
		});
		***/
		
		
		Button callTn = (Button) new Button(getBaseContext());
		callTn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText rawTn = (EditText) findViewById(R.id.raw_tn);
				MiscHelper mh = new MiscHelper(getBaseContext());
				mh.callNum(rawTn.getText().toString());
				
				
			}
		});
		
		/**
		IntlOnClickListener intlList = new IntlOnClickListener(getBaseContext());
		button.setOnClickListener(intlList);
		
		button = (Button) findViewById(R.id.delete_contact);
		button.setOnClickListener(intlList);
		
		button = (Button) findViewById(R.id.call_tn);
		button.setOnClickListener(intlList);*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//Log.i("In onCreateOptionsMenu", "Selected");
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//Log.i("In onOptionsItemSelected", "Selected");
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public LoadListAdapter readContactData() {
		//Log.i("In ReadContactData", " Entered readContactData");
		String[] fromColumns = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER};
		int[] toViews = {R.id.display_name, R.id.phone_number};
		LoadListAdapter adapter = null;
		
		//Log.i("In ReadContactData", " Before creating contentresolver");
    	ContentResolver cr = getBaseContext().getContentResolver();
		
		//Query to get contact name
    	//Log.i("In ReadContactData", " Before Calling Cursor");
    	
    	Cursor cur = cr
				.query(ContactsContract.Contacts.CONTENT_URI,
						null,
						null,
						null,
						"DISPLAY_NAME ASC");
    	//Log.i("In ReadContactData", " After Calling Cursor " + cur.getCount());
		// If data data found in contacts 
		if (cur.getCount() > 0) {
			
			//Log.i("In ReadContactData", "Pushing Contact");
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> number = new ArrayList<String>();
			
			while(cur.moveToNext()){
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
				{
					
					Cursor phoneCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					          fromColumns,
					          Phone.CONTACT_ID + "=?" ,
					          new String[] {String.valueOf(id)}, null);
		
					names.add(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
					//Log.i("AutocompleteContacts", "Phone Counts " + phoneCur.getCount());
					phoneCur.moveToFirst();
					number.add(phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					phoneCur.close();
					//adapter = new SimpleCursorAdapter(this, R.layout.phonename, phoneCur, fromColumns, toViews, 0);
					adapter = new LoadListAdapter(getBaseContext(), names, number);
				}
			}
		}
		cur.close();
		return adapter;
    }
}
