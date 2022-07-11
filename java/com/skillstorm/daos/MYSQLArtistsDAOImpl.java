package com.skillstorm.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.sql.Statement;
import java.util.List;

import com.skillstorm.conf.ChinookDBcreds;
import com.skillstorm.models.Artist;

public class MYSQLArtistsDAOImpl implements ArtistDAO {

	/*
	 * Steps for JDBC:
	 * 1. Load the JDBC Driver into memory
	 * 2. Establish a connection using said Driver object
	 * 3. Create an SQL statement
	 * 4. Use the connection object to execute the SQL statement
	 * 5. Parse the returned ResulteSet object for the data we want
	 * 6. Close the connection
	 */
	
	//public static final ChinookDBcreds creds = ChinookDBcreds.getInstance();
	
	/**
	 * @return The list of artists from the database if successful. Null in the event of failure
	 */
	@Override
	public List<Artist> findAll() {
		String sql = "SELECT * FROM artist";
		
		// Connection will auto close in event of failure due to Autoclosable
		try (Connection conn = ChinookDBcreds.getInstance().getConnection()) {
			// Create a Statement using the Connection object
			Statement stmt = conn.createStatement();

			// Executing the query returns a ResultSet which contains all of the values returned
			ResultSet rs = stmt.executeQuery(sql);			
			LinkedList<Artist> artists = new LinkedList<>();
			
			// next() returns a boolean on whether the iterator is done yet
			// You need to advance the cursor with it so that you can parse all of the results
			while(rs.next()) {
				// Looping over individual rows of the result set
				Artist artist = new Artist(rs.getInt("ArtistId"), rs.getString("Name"));
				artists.add(artist);
			}
			
			return artists;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		//DriverManager.getConnection(creds.getUrl(), creds.getUsername(), creds.getPassword());
		
		// Failure if received null
		return null;
	}
	
	/**
	 * @return The Artist with the given id if found, null if the artist does not exist
	 */
	@Override
	public Artist findById(int id) {
		String sql = "SELECT * FROM artist WHERE ArtistId = " + id;
		try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) // Since there's only one
				return new Artist(rs.getInt(1), rs.getString(2));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Artist findByName(String name) {
		// This is bad
		//String sql = "SELECT * FROM artist WHERE name = " + name;
		
		// Use parameterized queries
		String sql = "SELECT * FROM artist WHERE name = ?";
		
		try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
//			Statement stmt = conn.createStatement();
						
			// Instead of using Statement, we will use Prepared Statement
			PreparedStatement ps = conn.prepareStatement(sql);
			// Java will check our statement ahead of time to make sure it's okay
			ps.setString(1, name); // This auto escapes any malicious characters
			ResultSet rs = ps.executeQuery();
			if (rs.next()) //
				return new Artist(rs.getInt(1), rs.getString(2));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * ACID Compliance
	 * 
	 * Atomicity - Treating multiple operations as a single unit. If any fails, they all fail together.
	 * Consistency - Data is consistent with the schema. Anything that doesn't comply will not succeed.
	 * Isolation - Each database transaction is isolated and one transaction shouldn't result in another failing
	 * 			   just because they happened at the same time. Add mutexes to these fields
	 * Durability - In event of failure (power outage), the data is persisted. 
	 */
	
	@Override
	public Artist save(Artist artist) {
		// If this was auto-increment, then artistid is not needed
		String sql = "INSERT INTO artist (Artistid, Name) Values(?, ?)";
		try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
			
			// Start a transaction
			conn.setAutoCommit(false); // Prevents each query from immediately altering the database
			
			// Obtain auto incremented values
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, artist.getId());
			ps.setString(2, artist.getName());
			
			int rowsAffected = ps.executeUpdate(); // If 0, data didn't save
			if (rowsAffected != 0) {
				// If I want my keys
				ResultSet keys = ps.getGeneratedKeys();
				// List of all generated keys
				if (keys.next()) {
					int key = keys.getInt(1); // Give me auto generated key
					artist.setId(key);
					return artist;
				}
				conn.commit(); // Executes ALL queries in a given transaction
				return artist;
			} else {
				conn.rollback(); // Undoes any of the queries
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Artist artist) {
		String sql = "UPDATE artist WHERE ArtistId = ?";
		try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
			
			// Start a transaction
			conn.setAutoCommit(false); // Prevents each query from immediately altering the database
			
			// Obtain auto incremented values
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, artist.getId());
			
			int rowsAffected = ps.executeUpdate(); // If 0, data didn't save
			if (rowsAffected != 0) {			
				conn.commit(); // Executes ALL queries in a given transaction
			} else {
				conn.rollback(); // Undoes any of the queries
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM artist WHERE ArtistId = ?";
		try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
			
			// Start a transaction
			conn.setAutoCommit(false); // Prevents each query from immediately altering the database
			
			// Obtain auto incremented values
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			int rowsAffected = ps.executeUpdate(); // If 0, data didn't save
			if (rowsAffected != 0) {			
				conn.commit(); // Executes ALL queries in a given transaction
			} else {
				conn.rollback(); // Undoes any of the queries
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	@Override
	public void delete(Artist artist) {
		String sql = "DELETE FROM artist WHERE name = ?";
		try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
			
			// Start a transaction
			conn.setAutoCommit(false); // Prevents each query from immediately altering the database
			
			// Obtain auto incremented values
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, artist.getName());
			
			int rowsAffected = ps.executeUpdate(); // If 0, data didn't save
			if (rowsAffected != 0) {			
				conn.commit(); // Executes ALL queries in a given transaction
			} else {
				conn.rollback(); // Undoes any of the queries
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void deleteMany(int[] ids) {
		
		
			String sql = "DELETE FROM artist WHERE ArtistId = ?";
			try(Connection conn = ChinookDBcreds.getInstance().getConnection()) {
				
				// Start a transaction
				conn.setAutoCommit(false); // Prevents each query from immediately altering the database
				
				// Obtain auto incremented values
				PreparedStatement ps = conn.prepareStatement(sql);
				for (int i = 0; i < ids.length; i++) {
				ps.setInt(1, ids[i]);
					
					int rowsAffected = ps.executeUpdate(); // If 0, data didn't save
					if (rowsAffected != 0) {			
						conn.commit(); // Executes ALL queries in a given transaction
					} else {
						conn.rollback(); // Undoes any of the queries
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
		
	}
	
}
