package com.skillstorm.models;

public class Container {

	private int id;
	private int transport_id;
	private int warehouse_id;
	private String location;
	private String transport_name;
	private int transport_size;
	
	public Container() {
		
	}
	
	public Container(int transport_id, int warehouse_id, String location, String transport_name, int transport_size) {
		setTransport_id(transport_id);
		setWarehouse_id(warehouse_id);
		setLocation(location);
		setTransport_name(transport_name);
		setTransport_size(transport_size);
	}

	public Container(int id, int transport_id, int warehouse_id, String location, String transport_name, int transport_size) {
		this(transport_id, warehouse_id, location, transport_name, transport_size);
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

	public String getTransport_name() {
		return transport_name;
	}

	public void setTransport_name(String transport_name) {
		this.transport_name = transport_name;
	}

	public int getTransport_size() {
		return transport_size;
	}

	public void setTransport_size(int transport_size) {
		this.transport_size = transport_size;
	}
	
	
}
