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
import com.skillstorm.DAO.ItemDAO;
import com.skillstorm.DAO.ItemDAOImpl;
import com.skillstorm.models.Item;
import com.skillstorm.models.Message;
import com.skillstorm.models.Sort;
import com.skillstorm.services.URLParserService;

@WebServlet(urlPatterns = "/items/*")
public class ItemServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -954487132744010736L;
	
	ItemDAO dao = new ItemDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {			
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			int id = Integer.parseInt(param[1]);
			if (param[2].equals("total")) {		
				int total = dao.getTotalSpace(id);
				resp.setStatus(200);
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(total));
				
			} else {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Not a valid url")));	
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
			InputStream reqBody = req.getInputStream();
			Sort sortBy = mapper.readValue(reqBody, Sort.class);
			System.out.println(sortBy.toString());
			
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			int id = Integer.parseInt(param[1]);
			List<Item> items = dao.findAll(id, sortBy.getSort(), sortBy.getDirection());
			resp.setStatus(200);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(items));
			
		} catch (Exception e) {
			//e.printStackTrace();
			resp.setStatus(404);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Invalid container id")));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream reqBody = req.getInputStream();
		Item item = mapper.readValue(reqBody, Item.class);
		item = dao.save(item);
		
		if (item == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Error creating item")));
		} else {
			resp.setStatus(201);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(item));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream reqBody = req.getInputStream();
		Item item = mapper.readValue(reqBody, Item.class);
		item = dao.update(item);
		
		if (item == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Error updating item")));
		} else {
			resp.setStatus(200);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(item));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			
			int id = Integer.parseInt(param[1]);
			if (dao.delete(id)) {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Sucessfully deleted item with id " + id)));
			} else {
				resp.setStatus(400);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Unable to delete item")));
			}

		} catch (Exception e) {
			
		}
	}
}
