package com.skillstorm.models;

public class Sort {
	
	private String sort;
	private String direction;
	
	public Sort () {}
	
	public Sort(String sort, String direction) {
		setSort(sort);
		setDirection(direction);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "Sort [sort=" + sort + ", direction=" + direction + "]";
	}
	
	
}
