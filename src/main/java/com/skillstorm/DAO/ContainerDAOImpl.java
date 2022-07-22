package com.skillstorm.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.conf.WarehouseDBcreds;
import com.skillstorm.models.Container;

public class ContainerDAOImpl implements ContainerDAO {
	
	/**
	 * @param Takes in column to sort by and the direction to sort it, asc or desc
	 * @return Returns the full list of containers with the specified warehouse id. Returns null if nothing is found
	 */
	@Override
	public List<Container> findAll(int id, String sort, String direction) {
		String sql = "SELECT * FROM container INNER JOIN transport USING(transport_id) WHERE warehouse_id = " + id;
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			String sortBy = " ORDER BY " + sort + " " + direction;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql + sortBy);
			LinkedList<Container> containers = new LinkedList<>();
			
			// Traverse ResultSet
			while(rs.next()) {
				Container container = new Container(rs.getInt("container_id"), rs.getInt("transport_id"), 
						rs.getInt("warehouse_id"), rs.getString("location"), rs.getString("transport_name"),
						rs.getInt("transport_size"));
				containers.add(container);
			}
			
			return containers;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		// Failure if received null
		return null;
	}

	/**
	 * Used for the front-end search bar
	 * @param Takes in warehouse id and the container id to search for
	 * @return Returns a list of containers that have that number in the id. Returns null if nothing is found.
	 */
	@Override
	public List<Container> findByParam(int id, int wh_id) {
		String sql = "SELECT * FROM container INNER JOIN transport USING(transport_id) WHERE warehouse_id = ? AND container_id LIKE ?";
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, wh_id);
			ps.setString(2, id + "%");

			ResultSet rs = ps.executeQuery();
			LinkedList<Container> containers = new LinkedList<>();
			
			while (rs.next()) {
				Container container = new Container(rs.getInt("container_id"), rs.getInt("transport_id"), 
						rs.getInt("warehouse_id"), rs.getString("location"), rs.getString("transport_name"),
						rs.getInt("transport_size"));
				containers.add(container);
			}
			
			return containers;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * Used for the front-end search bar
	 * @param Takes in warehouse id and the location to search for
	 * @return Returns a list of containers that have that location in the id. Returns null if nothing is found.
	 */
	@Override
	public List<Container> findByParam(String location, int wh_id) {
		String sql = "SELECT * FROM container INNER JOIN transport USING(transport_id) WHERE warehouse_id = ? AND location LIKE ?";
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, wh_id);
			ps.setString(2, location + "%");
			ResultSet rs = ps.executeQuery();
			LinkedList<Container> containers = new LinkedList<>();
			
			while (rs.next()) {
				Container container = new Container(rs.getInt("container_id"), rs.getInt("transport_id"), 
						rs.getInt("warehouse_id"), rs.getString("location"), rs.getString("transport_name"),
						rs.getInt("transport_size"));
				containers.add(container);
			}
			
			return containers;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * Used for the front-end search bar
	 * @param Takes in warehouse id and the name to search for
	 * @return Returns a list of containers while searching for the string. Returns null if nothing is found.
	 */
	@Override
	public List<Container> findByItem(String name, int wh_id) {
		String sql = "SELECT * FROM container INNER JOIN transport USING(transport_id) INNER JOIN item USING(container_id) WHERE warehouse_id = ? AND name LIKE ?";
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, wh_id);
			ps.setString(2, name + "%");
			ResultSet rs = ps.executeQuery();
			LinkedList<Container> containers = new LinkedList<>();
			
			while (rs.next()) {
				Container container = new Container(rs.getInt("container_id"), rs.getInt("transport_id"), 
						rs.getInt("warehouse_id"), rs.getString("location"), rs.getString("transport_name"),
						rs.getInt("transport_size"));
				containers.add(container);
			}
			
			return containers;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


	@Override
	public Container save(Container container) {
		String sql = "INSERT INTO container VALUES(?, ?, ?, ?)";
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"});
			ps.setInt(1, container.getId());
			ps.setInt(2, container.getTransport_id());
			ps.setInt(3, container.getWarehouse_id());
			ps.setString(4, container.getLocation());
			
			int rowsAffected = ps.executeUpdate();
			//System.out.println(rowsAffected);
			if (rowsAffected != 0) {
				//System.out.println("Grabbing keys");
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1); // Give me auto generated key
					container.setId(key);				
				}
				conn.commit();
				return container;
			} else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public Container update(Container container) {
		String sql = "UPDATE container SET "
				+ "transport_id = ? , "
				+ "location = ? "
				+ "WHERE container_id = ?";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, container.getTransport_id());
			ps.setString(2, container.getLocation());
			ps.setInt(3, container.getId());
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
				return container;
			} else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM container WHERE container_id = ?";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Used to delete several ids in the container database
	 * @param Takes in an array of ids
	 */
	@Override
	public void deleteMany(int[] ids) {
		String sql = "DELETE FROM container WHERE container_id = ?";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//int rowsAffected = 0;
			for (int i : ids) {
				ps.setInt(1, i);
				if (ps.executeUpdate() != 0) {
					//rowsAffected++;
				}
				else {
					conn.rollback();
				}
			}
			
			conn.commit();

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}


}
