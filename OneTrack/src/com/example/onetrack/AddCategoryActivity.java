package com.example.onetrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SQLiteDB.Category;
import com.example.SQLiteDB.TrackerDBHelper;

public class AddCategoryActivity extends ActionBarActivity {
	
	TextView mCategoryText;
	EditText mCategoryEdit;
	Button mSubmitButton;
	Button mBackButton;
	
	TrackerDBHelper db;
	
	public static final String ADD_CATEGORY_BACK = "add category back";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_category);
		
		mCategoryText = (TextView)findViewById(R.id.categoryText);
		mCategoryEdit = (EditText)findViewById(R.id.categoryEdit);
		mSubmitButton = (Button)findViewById(R.id.submitBtn);
		mBackButton = (Button)findViewById(R.id.backBtn);
		
		db = new TrackerDBHelper(this);
	}
	
	public void onSubmitBtnClick(View view){
		String categoryName = mCategoryEdit.getText().toString();
		Category category = new Category(0, categoryName);
		db.addCategory(category);
		mCategoryEdit.setText("");
		Toast toast = Toast.makeText(this, "Category added", Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void onBackBtnClick(View view){
		//Intent intent = new Intent(this,MainActivity.class);
		//intent.putExtra(ADD_CATEGORY_BACK, 1);
		this.finish();
		//startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_category, menu);
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
