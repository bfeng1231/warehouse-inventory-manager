package com.skillstorm.servlets;

import java.io.IOException;

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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			int id = Integer.parseInt(param[1]);
			System.out.println(id);
			Warehouse warehouse = dao.findByParam(id);
			
			if (warehouse == null) {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new Message("No warehouse with that id")));
			} else {
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(warehouse));
			}
		}
		
	}
}
