package com.example.SQLiteDB;

public class Item {
	
	private int id;
	private int categoryId;
	private String category;
	private double itemPrice;
	private String itemComment;
	private String itemDate;
	
	public Item(int id, int categoryId, String category, double itemPrice, String itemComment, String itemDate){
		this.id = id;
		this.categoryId = categoryId;
		this.category = category;
		this.itemPrice = Math.round(itemPrice*100.0)/100.0;
		this.itemComment = itemComment;
		this.itemDate = itemDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = Math.round(itemPrice*100.0)/100.0;
	}

	public String getItemComment() {
		return itemComment;
	}

	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}

	public String getitemDate() {
		return itemDate;
	}

	public void setitemDate(String itemDate) {
		this.itemDate = itemDate;
	}
	
	
	
}
