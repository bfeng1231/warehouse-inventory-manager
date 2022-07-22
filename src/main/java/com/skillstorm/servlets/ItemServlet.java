package com.skillstorm.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
import com.skillstorm.models.RequestObj;
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

	/**
	 * @return Returns a list of items, a single item, or an integer depending on the extracted params
	 * 			and the query parameters
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check url params if we need the total amount of space the items are taking up
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
			// Only 1 param extracted so we get all the items in the container
			try {
				
				String[] param = urlService.extractParamFromURL(req.getPathInfo());
				Map<String, String[]> params = req.getParameterMap();
				
				int id = Integer.parseInt(param[1]);
				List<Item> items = dao.findAll(id, params.get("sort")[0], params.get("order")[0]);
				resp.setStatus(200);
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(items));
				
			} catch (NullPointerException error) {
				// There is no sorting data, so we look for a specific item with the id
				String[] param = urlService.extractParamFromURL(req.getPathInfo());
				int id = Integer.parseInt(param[1]);
				Item item = dao.findByParam(id);
				
				if (item == null) {
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString(new Message("No item with id " + id + " found")));
				} else {
					resp.setStatus(200);
					resp.setContentType("application/JSON");
					resp.getWriter().print(mapper.writeValueAsString(item));
				}
				
			}			
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
	
	/**
	 * Deletes several ids from the database
	 * @param Takes in the body from the http request which is an array of ids
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			
			InputStream reqBody = req.getInputStream();
			RequestObj ids = mapper.readValue(reqBody, RequestObj.class);
			
			if (dao.deleteMany(ids.getIds())) {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Sucessfully deleted items")));
			} else {
				resp.setStatus(400);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Unable to delete items")));
			}

		} catch (Exception e) {
			
		}
	}
}
