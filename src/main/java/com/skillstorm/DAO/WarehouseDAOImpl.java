package com.skillstorm.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.conf.WarehouseDBcreds;
import com.skillstorm.models.Warehouse;

public class WarehouseDAOImpl implements WarehouseDAO {

	@Override
	public List<Warehouse> findAll() {
		String sql = "SELECT * FROM warehouse";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			LinkedList<Warehouse> warehouses = new LinkedList<>();
			while (rs.next()) {
				Warehouse warehouse = new Warehouse(rs.getInt("warehouse_id"), rs.getInt("warehouse_size"), rs.getString("address"), rs.getInt("zipcode"));
				warehouses.add(warehouse);
			}
			
			return warehouses;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@Override
	public Warehouse findByParam(int id) {
		String sql = "SELECT * FROM warehouse WHERE warehouse_id = " + id;
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return new Warehouse(rs.getInt("warehouse_id"), rs.getInt("warehouse_size"), rs.getString("address"), rs.getInt("zipcode"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public Warehouse findByParam(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return Returns the total amount of space that the containers are taking up in the specific warehouse
	 */
	@Override
	public int getCurrentSpace(int id) {
		String sql = "SELECT sum(transport_size) FROM container INNER JOIN transport USING(transport_id) WHERE warehouse_id = " + id;
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	@Override
	public Warehouse save(Warehouse warehouse) {
		String sql = "INSERT INTO warehouse VALUES (?, ?, ?, ?)";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"});
			ps.setInt(1, warehouse.getWarehouse_id());
			ps.setInt(2, warehouse.getWarehouse_size());
			ps.setString(3, warehouse.getAddress());
			ps.setInt(4, warehouse.getZipcode());
			
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected != 0) {
				ResultSet keys = ps.getGeneratedKeys();
				if (keys.next()) {
					int key = keys.getInt(1); // Give me auto generated key
					warehouse.setWarehouse_id(key);				
				}
				
				conn.commit();
				
				return warehouse;
				
			} else {
				conn.rollback();
			}
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public Warehouse update(Warehouse warehouse) {
		String sql = "UPDATE warehouse SET "
				+ "warehouse_size = ? , "
				+ "address = ? , "
				+ "zipcode = ? "
				+ "WHERE warehouse_id = ?";
		
		try (Connection conn = WarehouseDBcreds.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, warehouse.getWarehouse_size());
			ps.setString(2, warehouse.getAddress());
			ps.setInt(3, warehouse.getZipcode());
			ps.setInt(4, warehouse.getWarehouse_id());
			
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected != 0) {
				conn.commit();
				return warehouse;
			} else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM warehouse WHERE warehouse_id = ?";
		
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

	

}
