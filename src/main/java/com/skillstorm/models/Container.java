package com.skillstorm.models;

public class Container {

	private int id;
	private int transport_id;
	private int warehouse_id;
	private String location;
	
	public Container() {
		
	}
	
	public Container(int transport_id, int warehouse_id, String location) {
		setTransport_id(transport_id);
		setWarehouse_id(warehouse_id);
		setLocation(location);
	}

	public Container(int id, int transport_id, int warehouse_id, String location) {
		this(transport_id, warehouse_id, location);
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransport_id() {
		return transport_id;
	}

	public void setTransport_id(int transport_id) {
		this.transport_id = transport_id;
	}

	public int getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(int warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Container [id=" + id + ", transport_id=" + transport_id + ", warehouse_id=" + warehouse_id
				+ ", location=" + location + "]";
	}
	
	
}
