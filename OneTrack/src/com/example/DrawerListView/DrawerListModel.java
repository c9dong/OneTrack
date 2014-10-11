package com.example.DrawerListView;

import com.example.SQLiteDB.Category;

public class DrawerListModel {
	
	private int tableId;
	private int iconImage;
	private String categoryName;
	
	public DrawerListModel(int tableId, int iconImage, String categoryName){
		this.tableId = tableId;
		this.iconImage = iconImage;
		this.categoryName = categoryName;
	}
	
	public int getTableId(){
		return tableId;
	}
	public void setTableId(int tableId){
		this.tableId = tableId;
	}
	public int getIconImage() {
		return iconImage;
	}
	public void setIconImage(int iconImage) {
		this.iconImage = iconImage;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category toCategory(){
		Category category = new Category(tableId, categoryName);
		return category;
	}
}
