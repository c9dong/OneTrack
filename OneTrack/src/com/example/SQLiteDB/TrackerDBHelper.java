package com.example.SQLiteDB;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrackerDBHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 2;
	
	//Database name
	private static final String DATABASE_NAME = "Tracker";
	
	//Table name
	private static final String CATEGORY_TABLE_NAME = "Category";
	
	//Column names
	private static final String CATEGORY_ID = "Id";
	private static final String CATEGORY_NAME = "CategoryName";
	
	private static final String CATEGORY_TABLE_CREATE =
			"CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME + " (" + 
			CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			CATEGORY_NAME + " TEXT);";
	
	private static final String CATEGORY_TABLE_INITIALIZE_ALL = 
			"INSERT INTO " + CATEGORY_TABLE_NAME + " " + 
			"( " + CATEGORY_NAME + " ) " +  
			"VALUES ('All'); ";
	private static final String CATEGORY_TABLE_INITIALIZE_ENTERTAINMENT = 
			"INSERT INTO " + CATEGORY_TABLE_NAME + " " + 
			"( " + CATEGORY_NAME + " ) " +  			
			"VALUES ('Entertainment'); ";
	private static final String CATEGORY_TABLE_INITIALIZE_GROCERY = 
			"INSERT INTO " + CATEGORY_TABLE_NAME + " " + 
			"( " + CATEGORY_NAME + " ) " +  
			"VALUES ('Grocery'); ";
	private static final String CATEGORY_TABLE_INITIALIZE_EDUCATION = 
			"INSERT INTO " + CATEGORY_TABLE_NAME + " " +
			"( " + CATEGORY_NAME + " ) " +  
			"VALUES ('Education'); ";
	
	private static final String CATEGORY_TABLE_DROP = 
			"DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;
	
	private static final String ITEM_TABLE_NAME = "Items";
	
	private static final String ITEM_ID = "Id";
	private static final String ITEM_CATEGORY_ID = "Category_Id";
	private static final String ITEM_CATEGORY = "Category";
	private static final String ITEM_PRICE = "Item_Price";
	private static final String ITEM_COMMENT = "Comment";
	private static final String ITEM_DATE = "Update_Date";
	
	private static final String ITEM_TABLE_CREATE = 
			"CREATE TABLE IF NOT EXISTS " + ITEM_TABLE_NAME + " ( " + 
					ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					ITEM_CATEGORY_ID + " INTEGER, " + 
					ITEM_CATEGORY + " TEXT, " +
					ITEM_PRICE + " TEXT, " +
					ITEM_COMMENT + " TEXT, " +
					ITEM_DATE + " TEXT);";
	
	private static final String ITEM_TABLE_DROP = 
			"DROP TABLE If EXISTS " + ITEM_TABLE_NAME;
	
	public TrackerDBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CATEGORY_TABLE_CREATE);
		db.execSQL(ITEM_TABLE_CREATE);
		db.execSQL(CATEGORY_TABLE_INITIALIZE_ALL);
		db.execSQL(CATEGORY_TABLE_INITIALIZE_ENTERTAINMENT);
		db.execSQL(CATEGORY_TABLE_INITIALIZE_GROCERY);
		db.execSQL(CATEGORY_TABLE_INITIALIZE_EDUCATION);
		
//		ContentValues values = new ContentValues();
//		values.put(ITEM_CATEGORY_ID, 2);
//		values.put(ITEM_CATEGORY, "Entertainment");
//		values.put(ITEM_PRICE, 1.2);
//		values.put(ITEM_COMMENT, "Hello");
//		values.put(ITEM_DATE, "2014-01-01");
//		db.insert(ITEM_TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(CATEGORY_TABLE_DROP);
		db.execSQL(ITEM_TABLE_DROP);
		onCreate(db);
	}
	
	public void addCategory(Category category){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(CATEGORY_NAME, category.getCategoryName());
		
		db.insert(CATEGORY_TABLE_NAME, null, values);
		db.close();
	}
	
	public Category getCategory(int id){
		String CATEGORY_SELECT = 
				"SELECT " + 
						CATEGORY_ID + ", " + 
						CATEGORY_NAME + " " +
				"FROM " + CATEGORY_TABLE_NAME + 
				"WHERE " + CATEGORY_ID + " = ?;";
		String[] selectArgs = {String.valueOf(id)};
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(CATEGORY_SELECT, selectArgs);
		Category category;
		if(cursor != null){
			cursor.moveToFirst();
			category = new Category(Integer.parseInt(cursor.getString(0)), 
					cursor.getString(1));
		}else{
			category = new Category(1,"All");
		}
		cursor.close();
		db.close();
		return category;
	}
	
	public ArrayList<Category> getAllCategory(){
		ArrayList<Category> categoryList = new ArrayList<Category>();
		String CATEGORY_SELECT_ALL = 
				"SELECT " + 
						CATEGORY_ID + ", " + 
						CATEGORY_NAME + " " +
				"FROM " + CATEGORY_TABLE_NAME;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(CATEGORY_SELECT_ALL, null);
		
		Category category;
		if(cursor != null){
			cursor.moveToFirst();
			do{
				category = new Category(Integer.parseInt(cursor.getString(0)), 
						cursor.getString(1));
				categoryList.add(category);
			}while(cursor.moveToNext());
		}
		cursor.close();
		db.close();
		
		return categoryList; 
	}
	
	public int getCategoryCount(){
		String CATEGORY_COUNT = 
				"SELECT * FROM " + CATEGORY_TABLE_NAME;;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(CATEGORY_COUNT, null);
		int count;
		if(cursor!=null){
			count = cursor.getCount();
		}else{
			count = 0;
		}
		cursor.close();
		db.close();
		return count;
	}
	
	public int updateCategory(Category category){
		String CATEGORY_UPDATE = 
				"UPDATE " + CATEGORY_TABLE_NAME + " " + 
						"SET " + CATEGORY_NAME + " = ? " + 
						"WHERE " + CATEGORY_ID + " = ?;";
		String[] selectArgs = {category.getCategoryName(), 
				String.valueOf(category.getId())};
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.rawQuery(CATEGORY_UPDATE, selectArgs);
		db.close();
		return 1;
	}
	
	public void deleteCategory(Category category){
		String CATEGORY_DELETE = 
				"DELETE FROM " + CATEGORY_TABLE_NAME + " " + 
						"WHERE " + CATEGORY_ID + " = ?;";
		String[] selectArgs = {String.valueOf(category.getId())};
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.rawQuery(CATEGORY_DELETE, selectArgs);
		db.close();
	}
	
	public Item getItem(int id){
		String ITEM_SELECT = 
				"SELECT " + 
						ITEM_ID + ", " + 
						ITEM_CATEGORY_ID + ", " +
						ITEM_CATEGORY + ", " +
						ITEM_PRICE + ", " +
						ITEM_COMMENT + ", " +
						ITEM_DATE + " " +
				"FROM " + ITEM_TABLE_NAME + " " +
				"WHERE " + ITEM_ID + " = ?;";
		String[] selectArg = {String.valueOf(id)};
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(ITEM_SELECT, selectArg);
		Item item = new Item(0,0,"none",0.0,"none","0000-00-00");
		if(cursor != null){
			cursor.moveToFirst();
			item = new Item(Integer.parseInt(cursor.getString(0)), 
								Integer.parseInt(cursor.getString(1)),
								cursor.getString(2),
								Double.parseDouble(cursor.getString(3)),
								cursor.getString(4),
								cursor.getString(5));
		}
		db.close();
		cursor.close();
		return item;
	}
	
	public ArrayList<Item> getAllItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		String ITEM_GET_ALL = 
				"SELECT " + 
						ITEM_ID + ", " + 
						ITEM_CATEGORY_ID + ", " +
						ITEM_CATEGORY + ", " +
						ITEM_PRICE + ", " +
						ITEM_COMMENT + ", " +
						ITEM_DATE + " " +
				"FROM " + ITEM_TABLE_NAME;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(ITEM_GET_ALL, null);
		Item item;
		if(cursor != null && cursor.getCount()>0){
			cursor.moveToFirst();
			do{
				item = new Item(Integer.parseInt(cursor.getString(0)), 
						Integer.parseInt(cursor.getString(1)),
						cursor.getString(2),
						Double.parseDouble(cursor.getString(3)),
						cursor.getString(4),
						cursor.getString(5));
				items.add(item); 
			}while(cursor.moveToNext());
		}
		db.close();
		cursor.close();
		return items;
	}
	
	public ArrayList<Item> getItemsByCategory(int categoryId){
		if(categoryId == 1){	
			//return getAllItems();
			return new ArrayList<Item>();
		}else{
			ArrayList<Item> items = new ArrayList<Item>();
			String ITEM_GET_CATEGORY = 
					"SELECT " + 
							ITEM_ID + ", " + 
							ITEM_CATEGORY_ID + ", " +
							ITEM_CATEGORY + ", " +
							ITEM_PRICE + ", " +
							ITEM_COMMENT + ", " +
							ITEM_DATE + " " +
					"FROM " + ITEM_TABLE_NAME + " " + 
					"WHERE " + ITEM_CATEGORY_ID + " = ?;";
			String[] selectArgs = {String.valueOf(categoryId)};
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(ITEM_GET_CATEGORY, selectArgs);
			Item item;
			if(cursor != null && cursor.getCount()>0){
				cursor.moveToFirst();
				do{
					item = new Item(Integer.parseInt(cursor.getString(0)), 
							Integer.parseInt(cursor.getString(1)),
							cursor.getString(2),
							Double.parseDouble(cursor.getString(3)),
							cursor.getString(4),
							cursor.getString(5));
					items.add(item);
				}while(cursor.moveToNext());
			}
			db.close();
			cursor.close();
			return items;
		}
		
	}
	
	public void addItem(Item item){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(ITEM_CATEGORY_ID, item.getCategoryId());
		values.put(ITEM_CATEGORY, item.getCategory());
		values.put(ITEM_PRICE, item.getItemPrice());
		values.put(ITEM_COMMENT, item.getItemComment());
		values.put(ITEM_DATE, item.getitemDate());
		
		db.insert(ITEM_TABLE_NAME, null, values);
		db.close();
	}
	
	public void dropTable(){
		String CATEGORY_DROP = 
				"DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(CATEGORY_DROP);
		//db.rawQuery(CATEGORY_DROP, null);
		db.close();
	}
	
}
