package com.skillstorm.models;

public class Item {
	
	private int item_id;
	private int container_id;
	private String name;
	private int units;
	private int size;
	private String datetime;
	
	public Item () {}
	
	public Item (String name, int size, int units, int container_id, String datetime) {
		setContainer_id(container_id);
		setName(name);
		setUnits(units);
		setSize(size);
		setDatetime(datetime);
	}
	
	public Item (int id, String name, int size, int units, int container_id, String datetime) {
		this(name, size, units, container_id, datetime);
		setItem_id(id);
	}

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getContainer_id() {
		return container_id;
	}

	public void setContainer_id(int container_id) {
		this.container_id = container_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	
	
	
}
