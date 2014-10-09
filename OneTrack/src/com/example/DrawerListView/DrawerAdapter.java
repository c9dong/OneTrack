package com.example.DrawerListView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onetrack.R;

public class DrawerAdapter extends ArrayAdapter<DrawerListModel>{
	
	private Context context;
	private int resource;
	private int layoutResourceId;
	private DrawerListModel data[] = null;

	public DrawerAdapter(Context context, int resource, int layoutResourceId,
			DrawerListModel[] data) {
		super(context, resource, layoutResourceId, data);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = resource;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
		
	public static class ViewHolder{
		public ImageView iconImage;
		public TextView categoryName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		
		if(row == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
			row = inflater.inflate(resource, parent, false);
		}
		DrawerListModel model = data[position];
		if(model != null){
			ImageView iconImage = (ImageView)row.findViewById(R.id.iconImage);
			TextView categoryText = (TextView)row.findViewById(R.id.categoryName);
			if(iconImage!=null){
				iconImage.setImageResource(model.getIconImage());
			}
			if(categoryText!=null){
				categoryText.setText(model.getCategoryName());
			}
		}
		
		return row;
	}


}
