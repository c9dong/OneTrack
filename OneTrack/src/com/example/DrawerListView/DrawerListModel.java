package com.example.DrawerListView;

public class DrawerListModel {
	private int iconImage;
	private String categoryName;
	
	public DrawerListModel(int iconImage, String categoryName){
		this.iconImage = iconImage;
		this.categoryName = categoryName;
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
}
