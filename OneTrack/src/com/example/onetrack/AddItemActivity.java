package com.example.onetrack;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SQLiteDB.Category;
import com.example.SQLiteDB.Item;
import com.example.SQLiteDB.TrackerDBHelper;

public class AddItemActivity extends ActionBarActivity {
	
	TrackerDBHelper db;
	
	Spinner category;
	TextView priceText;
	EditText priceEdit;
	TextView commentText;
	EditText commentEdit;
	DatePicker datePicker;
	Button submitBtn;
	Button cancelBtn;
	
	private int mCurrentSpinnerSelected;
	
	ArrayAdapter<Category> categoryAdapter;
	Category[] categorySpinnerVal;
	public static final String RETURN_ID = "return_id";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		
		category = (Spinner)findViewById(R.id.categories);
		priceText = (TextView)findViewById(R.id.priceText);
		priceEdit = (EditText)findViewById(R.id.priceEdit);
		commentText = (TextView)findViewById(R.id.commentText);
		commentEdit = (EditText)findViewById(R.id.commentEdit);
		datePicker = (DatePicker)findViewById(R.id.datePicker);
		submitBtn = (Button)findViewById(R.id.submitBtn);
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		
		Intent intent = getIntent();
		int categoryId = intent.getIntExtra(NavigationDrawerFragment.CATEGORY_ID, 1);
		
		db = new TrackerDBHelper(this);
		populateSpinner();
		
		mCurrentSpinnerSelected = findSpinnerPosition(categoryId);
		
		category.setSelection(mCurrentSpinnerSelected);
		
		
	}
	
	private int findSpinnerPosition(int categoryId){
		for(int i=0;i<categorySpinnerVal.length;i++){
			if(categorySpinnerVal[i].getId()==categoryId){
				return i;
			}
		}
		return 0;
	}
	
	private void populateSpinner(){
		ArrayList<Category> categoryList = db.getAllCategory();
		int size = categoryList.size();
		categorySpinnerVal = new Category[size];
		for(int i=0;i<size;i++){
			categorySpinnerVal[i] = categoryList.get(i);
		}
		categoryAdapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item,categorySpinnerVal);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		category.setAdapter(categoryAdapter);
	}
	
	public void submitBtnClick(View view){
		int position = category.getSelectedItemPosition();
		Category category = categorySpinnerVal[position];
		int categoryId = category.getId();
		String categoryName = category.getCategoryName();
		String price = priceEdit.getText().toString();
		String comment = commentEdit.getText().toString();
		String date = String.valueOf(datePicker.getYear()) + "-" + String.valueOf(datePicker.getMonth())+ "-" + 
						String.valueOf(datePicker.getDayOfMonth());
		Item item = new Item(0,categoryId, categoryName, Double.parseDouble(price), comment, date);
		db.addItem(item);
		Log.v("category id", String.valueOf(categoryId));
		Log.v("category", categoryName);
		Log.v("price", price);
		Log.v("comment", comment);
		priceEdit.setText("");
		commentEdit.setText("");
		Toast toast = Toast.makeText(this, "Item added", Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void cancelBtnClick(View view){
		Intent intent = new Intent(this,MainActivity.class);
		int returnId = 2;
		intent.putExtra(RETURN_ID, returnId);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
