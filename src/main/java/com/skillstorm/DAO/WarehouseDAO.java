package com.skillstorm.DAO;

import com.skillstorm.models.Warehouse;

public interface WarehouseDAO {
	
	// Read methods
	public Warehouse findByParam(int id);
	public Warehouse findByParam(String name);
	public int getCurrentSpace(int id);
	
}
