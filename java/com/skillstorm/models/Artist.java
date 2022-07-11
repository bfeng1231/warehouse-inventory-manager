package com.skillstorm.models;

public class Artist {
	
	// POJO (Plain Ole Java Object)
	
	private int id;
	private String name;
	
	public Artist() {
		
	}
	
	// Use for auto increment ids
	public Artist(String name) {
		this.name = name;
	}

	public Artist(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + "]";
	}
	
}
