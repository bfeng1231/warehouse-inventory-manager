package com.skillstorm.models;

public class Warehouse {

	private int id;
	private int size;
	private String address;
	private int zipcode;
	private String state;
	private String city;
	
	
	public Warehouse(int size, String address, int zipcode, String state, String city) {
		setSize(size);
		setAddress(address);
		setZipcode(zipcode);
		setState(state);
		setCity(city);
	}

	public Warehouse(int id, int size, String address, int zipcode, String state, String city) {
		this(size, address, zipcode, state, city);
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
