package com.skillstorm.daos;

import java.util.List;

import com.skillstorm.models.Artist;

// Queries specific to artists
public interface ArtistDAO {

	public List<Artist> findAll();
	public Artist findById(int id);
	public Artist findByName(String name);
	// Return artist since we can get the key (auto incremented)
	// Can use artist to test if it's in the table
	public Artist save(Artist artist);
	public void update(Artist artist); // Contains the id and updates as needed
	public void delete(int id);
	public void delete(Artist artist);
	public void deleteMany(int[] ids);
}
