package com.example.ItemListView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DrawerListView.DrawerListModel;
import com.example.SQLiteDB.Category;
import com.example.SQLiteDB.Item;
import com.example.SQLiteDB.TrackerDBHelper;
import com.example.onetrack.MainActivity;
import com.example.onetrack.R;

public class ItemAdapter extends ArrayAdapter<Item>{
	
	private Context context;
	private int resource;
	private int layoutResourceId;
	private Item data[] = null;
	
	public ItemAdapter(Context context, int resource, int layoutResourceId,
			Item[] data) {
		super(context, resource, layoutResourceId, data);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = resource;
		this.layoutResourceId = layoutResourceId;
		this.data = data;	
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		
		if(row == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
			row = inflater.inflate(resource, parent, false);
		}
		Item model = data[position];
		if(model != null){
			TextView dateText = (TextView)row.findViewById(R.id.dateText);
			TextView priceText = (TextView)row.findViewById(R.id.priceText);
			TextView commentText = (TextView)row.findViewById(R.id.commentText);
			Button deleteBtn = (Button)row.findViewById(R.id.deleteBtn);

			if(dateText!=null){
				dateText.setText(model.getitemDate());
			}
			if(priceText!=null){
				priceText.setText(String.valueOf(model.getItemPrice()));
			}
			if(commentText!=null){
				commentText.setText(model.getItemComment());
			}
			deleteBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					TrackerDBHelper db = new TrackerDBHelper(context);
					Item i = data[position];
					//c.setId(2);
					db.deleteItem(i);
					Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
					Intent refresh = new Intent(context, MainActivity.class);
					context.startActivity(refresh);
				}
				
			});
		}
		
		return row;
	}

	
}
