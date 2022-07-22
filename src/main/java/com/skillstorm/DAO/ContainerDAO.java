package com.skillstorm.DAO;

import java.util.List;

import com.skillstorm.models.Container;

public interface ContainerDAO {
	
	// Read methods
	public List<Container> findAll(int id, String sort, String direction);
	public List<Container> findByParam(int id, int wh_id);
	public List<Container> findByParam(String location, int wh_id);
	public List<Container> findByItem(String name, int wh_id);
	
	// Create methods
	public Container save(Container container);
	
	// Update methods
	public Container update(Container container); 
	
	// Delete methods
	public boolean delete(int id);
	public void deleteMany(int[] ids);
	
}
