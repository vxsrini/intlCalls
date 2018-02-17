package com.example.intlcall;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class LoadListAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final ArrayList<String> names;
	private final ArrayList<String> number;
	private final ArrayList<String> namesTmp;
	private final ArrayList<String> numberTmp;

	public LoadListAdapter(Context context, ArrayList<String> names, ArrayList<String> number) {
		super(context, R.layout.phonename, names);
		this.context = context;
		this.names = names;
		this.number = number;
		namesTmp = new ArrayList<String>();
		namesTmp.addAll(names);
		numberTmp = new ArrayList<String>();
		numberTmp.addAll(number);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.phonename, parent, false);
	    TextView name = (TextView) rowView.findViewById(R.id.display_name);
	    Log.i("In getView - ", String.valueOf(position) + " : " + String.valueOf(names.size()) + " : " + String.valueOf(namesTmp.size()));
	    Log.i("In getView - ", String.valueOf(number.size()) + " : " + String.valueOf(numberTmp.size()));
	    if (position < names.size()){
		    name.setText(names.get(position));
		    TextView phoneNum = (TextView) rowView.findViewById(R.id.phone_number);
		    phoneNum.setText(number.get(position));
	    }
	    
		rowView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView contactTn = (TextView) v.findViewById(R.id.phone_number);
				MiscHelper mh = new MiscHelper(context);
				mh.callNum(contactTn.getText().toString());
			}
		});
	    return rowView;
	}
	
	public void filter (CharSequence charText){
		charText = charText.toString().toLowerCase(Locale.getDefault());
		names.clear();
		number.clear();
		//notifyDataSetChanged();
		if (charText.length() == 0){
			names.addAll(namesTmp);
			number.addAll(numberTmp);
		
		}
		else {
			for (int count = 0; count < namesTmp.size(); count++ ){
				if (namesTmp.get(count).toLowerCase(Locale.getDefault()).contains(charText)){
					names.add(namesTmp.get(count));
					number.add(numberTmp.get(count));
				}
			}
		}
		notifyDataSetChanged();
	}
}
