package com.skillstorm.DAO;

import java.util.List;

import com.skillstorm.models.Warehouse;

public interface WarehouseDAO {
	
	// Read methods
	public List<Warehouse> findAll();
	public Warehouse findByParam(int id);
	public Warehouse findByParam(String name);
	public int getCurrentSpace(int id);
	
	// Create methods
	public Warehouse save(Warehouse warehouse);
	
	// Update methods
	public Warehouse update(Warehouse warehouse);
	
	// Delete methods
	public boolean delete (int id);
}
