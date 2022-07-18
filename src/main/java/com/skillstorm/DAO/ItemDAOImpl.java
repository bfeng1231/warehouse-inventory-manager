package com.skillstorm.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.conf.WarehouseDBcreds;
import com.skillstorm.models.Item;

public class ItemDAOImpl implements ItemDAO{

	@Override
	public List<Item> findAll(int id, String sort, String direction) {
		String sql = "SELECT * FROM item WHERE container_id = ? ";
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			String sortBy = "ORDER BY " + sort + " " + direction;
			PreparedStatement ps = conn.prepareStatement(sql + sortBy);
			ps.setInt(1, id);
			//System.out.println(ps);
			ResultSet rs = ps.executeQuery();

			//Statement stmt = conn.createStatement();
			//ResultSet rs = stmt.executeQuery(sql);
			LinkedList<Item> items = new LinkedList<>();
			
			while(rs.next()) {
				Item item = new Item(rs.getInt("item_id"), rs.getString("name"), rs.getInt("size"), 
						rs.getInt("units"), rs.getInt("container_id"), rs.getString("datetime"));
				items.add(item);
			}
			
			return items;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		// Failure if received null
		return null;
	}

	@Override
	public Item findByParam(int id) {
		String sql = "SELECT * FROM item WHERE item_id = " + id;
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				return new Item(rs.getInt("item_id"), rs.getString("name"), rs.getInt("size"), 
						rs.getInt("units"), rs.getInt("container_id"), rs.getString("datetime"));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Item findByParam(String name) {
		String sql = "SELECT * FROM item WHERE name = ?";
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new Item(rs.getInt("item_id"), rs.getString("name"), rs.getInt("size"), 
						rs.getInt("units"), rs.getInt("container_id"), rs.getString("datetime"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Item save(Item item) {
		
		String sql = "INSERT INTO item VALUES(?, ?, ?, ?, ?, NOW())";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"});
			ps.setInt(1, item.getItem_id());
			ps.setString(2, item.getName());
			ps.setInt(3, item.getSize());
			ps.setInt(4, item.getUnits());
			ps.setInt(5, item.getContainer_id());
					
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int id = keys.getInt(1);
					item.setItem_id(id);
				}
				conn.commit();
				return item;
			}
			conn.rollback();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Item update(Item item) {
		String sql = "UPDATE item SET "
				+ "name = ? , "
				+ "size = ? , "
				+ "units = ? , "
				+ "container_id = ? , "
				+ "datetime = NOW() "
				+ "WHERE item_id = ?";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()){
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, item.getName());
			ps.setInt(2, item.getSize());
			ps.setInt(3, item.getUnits());
			ps.setInt(4, item.getContainer_id());
			ps.setInt(5, item.getItem_id());
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
				return item;
			} else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
		
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM item WHERE item_id = ?";
		
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

	@Override
	public boolean deleteMany(List<Integer> ids) {
		String sql = "DELETE FROM item WHERE item_id = ?";
		
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
					return false;
				}
			}
			
			conn.commit();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getTotalSpace(int id) {
		String sql = "SELECT SUM(size * units) AS total_space FROM item WHERE container_id =" + id;
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getInt("total_space");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
