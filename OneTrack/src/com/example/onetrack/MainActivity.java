package com.example.onetrack;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ItemListView.ItemAdapter;
import com.example.SQLiteDB.Category;
import com.example.SQLiteDB.Item;
import com.example.SQLiteDB.TrackerDBHelper;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		Intent intent = getIntent();
		int return_id_item = intent.getIntExtra(AddItemActivity.RETURN_ID, -1);
		int return_id_category = intent.getIntExtra(AddCategoryActivity.ADD_CATEGORY_BACK, -1);
		if(return_id_item==2){
			Toast toast = Toast.makeText(this, "Canceled item", Toast.LENGTH_SHORT);
			toast.show();
		}
		if(return_id_category==1){
			Toast toast = Toast.makeText(this, "Canceled Category", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position, Category category) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1, category)).commit();
	}

	public void onSectionAttached(int position, int id, String title) {
		mTitle = title;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		private static final String ARG_SECTION_ID = "section_id";
		private static final String ARG_SECTION_NAME = "section_name";
		
		TextView sectionLabel;
		ListView itemContent;
		TrackerDBHelper db;
		
		Item[] itemListviewVal;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber, Category category) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			args.putInt(ARG_SECTION_ID, category.getId());
			args.putString(ARG_SECTION_NAME, category.getCategoryName());
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			sectionLabel = (TextView)rootView.findViewById(R.id.section_label);
			itemContent = (ListView)rootView.findViewById(R.id.itemContent);
			db = new TrackerDBHelper(getActivity());
			updateContent();
			return rootView;
		}
		
		public void updateContent(){
			String title = getArguments().getString(ARG_SECTION_NUMBER);
			int position = getArguments().getInt(ARG_SECTION_NUMBER);
			int id = getArguments().getInt(ARG_SECTION_ID);
			sectionLabel.setText(title);
			
			ArrayList<Item> items = db.getItemsByCategory(id);
			int size = items.size();
			itemListviewVal = new Item[size];
			String[] l = new String[size];
			for(int i=0;i<size;i++){
				itemListviewVal[i] = items.get(i);
				l[i] = String.valueOf(items.get(i).getItemPrice());
			}
			ItemAdapter a = new ItemAdapter(this.getActivity(), R.layout.main_item,
				android.R.id.text1, itemListviewVal);
			//ArrayAdapter<String> a = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,l);
			itemContent.setAdapter(a);
		}
		
		@Override
		public void onResume(){
			super.onResume();
			updateContent();
		}
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			String title = getArguments().getString(ARG_SECTION_NAME);
			int position = getArguments().getInt(ARG_SECTION_NUMBER);
			int id = getArguments().getInt(ARG_SECTION_ID);
			((MainActivity) activity).onSectionAttached(position, id, title);
		}

	}

}
