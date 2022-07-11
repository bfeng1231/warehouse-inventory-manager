package com.skillstorm.DAO;

import java.util.List;

import com.skillstorm.models.Container;

public interface ContainerDAO {
	
	// Read methods
	public List<Container> findAll();
	public Container findByParam(int id);
	public Container findByParam(String location);
	
	// Create methods
	public Container save(Container container);
	
	// Update methods
	public Container update(Container container); 
	
	// Delete methods
	public boolean delete(int id);
	public void deleteMany(int[] ids);
}
