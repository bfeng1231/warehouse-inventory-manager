package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.DAO.WarehouseDAO;
import com.skillstorm.DAO.WarehouseDAOImpl;
import com.skillstorm.models.Message;
import com.skillstorm.models.Warehouse;
import com.skillstorm.services.URLParserService;

@WebServlet(urlPatterns = "/warehouses/*")
public class WarehouseServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1255978588645665829L;

	WarehouseDAO dao = new WarehouseDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();
	
	/**
	 * @return Returns different data to the front end depending on what parameters are inserted
	 * @throws Throws an ArrayIndexOutOfBoundsException if there is only 1 parameter
	 * @throws Throws another ArrayIndexOutOfBoundsException if there are no parameter in the nested try catch
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check the url if we are getting the total space currently inside the warehouse
		try {
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			int id = Integer.parseInt(param[1]);
			if (param[2].equals("total")) {
				int total = dao.getCurrentSpace(id);
				resp.setStatus(200);
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(total));
			} else {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Not a valid url")));	
			}
				
		} catch (Exception e) {
			// Only 1 param so we look for a specific warehouse
			try {				
				String[] param = urlService.extractParamFromURL(req.getPathInfo());
				int id = Integer.parseInt(param[1]);
				Warehouse warehouse = dao.findByParam(id);
				
				if (warehouse == null) {
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString(new Message("No warehouse with that id")));
				} else {
					resp.setContentType("application/JSON");
					resp.getWriter().print(mapper.writeValueAsString(warehouse));
				}
				
			} catch (ArrayIndexOutOfBoundsException error) {
				// No params received so we search for all warehouses
				List<Warehouse> warehouses = dao.findAll();
				if (warehouses == null) {
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString(new Message("No warehouses")));
				} else {
					resp.setContentType("application/JSON");
					resp.getWriter().print(mapper.writeValueAsString(warehouses));
				}
			}
			
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class);
		System.out.println(warehouse.toString());	
		warehouse = dao.save(warehouse);
		
		if (warehouse == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Error creating container")));
		} else {
			resp.setStatus(201);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(warehouse));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Warehouse warehouse = mapper.readValue(reqBody, Warehouse.class);

		warehouse = dao.update(warehouse);
		
		if (warehouse == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Error updating container")));
		} else {
			resp.setStatus(201);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(warehouse));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			
			int id = Integer.parseInt(param[1]);
			if (dao.delete(id)) {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Sucessfully deleted warehouse with id " + id)));
			} else {
				resp.setStatus(400);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Unable to delete warehouse")));
			}

		} catch (Exception e) {
			
		}
	}
}
