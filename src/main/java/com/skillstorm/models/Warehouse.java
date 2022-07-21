package com.skillstorm.models;

public class Warehouse {

	private int warehouse_id;
	private int warehouse_size;
	private String address;
	private int zipcode;
	
	public Warehouse () {
		System.out.println("new warehouse");
	}
	
	public Warehouse (int warehouse_id, int warehouse_size , String address, int zipcode) {
		super();
		setWarehouse_id(warehouse_id);
		setWarehouse_size(warehouse_size);
		setAddress(address);
		setZipcode(zipcode);
		System.out.println(this.toString());
	}

	public int getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public int getWarehouse_size() {
		return warehouse_size;
	}

	public void setWarehouse_size(int warehouse_size) {
		this.warehouse_size = warehouse_size;
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

	@Override
	public String toString() {
		return "Warehouse [warehouse_id=" + warehouse_id + ", warehouse_size=" + warehouse_size + ", address=" + address
				+ ", zipcode=" + zipcode + "]";
	}

	
	
}
