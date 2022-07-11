package com.skillstorm.DAO;

import java.util.List;

import com.skillstorm.models.Item;

public interface ItemDAO {
	
	// Read methods
	public List<Item> findAll(int id, String sort, String direction);
	public Item findByParam(int id);
	public Item findByParam(String name);
	public int getTotalSpace(int id);
	
	// Create methods
	public Item save(Item item);
	
	// Update methods
	public Item update(Item item);
	
	// Delete methods
	public boolean delete(int id);
	public void deleteMany(int[] ids);
}
