package com.skillstorm.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.skillstorm.conf.WarehouseDBcreds;
import com.skillstorm.models.Warehouse;

public class WarehouseDAOImpl implements WarehouseDAO {

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

}
