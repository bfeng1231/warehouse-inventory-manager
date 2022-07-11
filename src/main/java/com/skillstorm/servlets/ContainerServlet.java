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
import com.skillstorm.DAO.ContainerDAO;
import com.skillstorm.DAO.ContainerDAOImpl;
import com.skillstorm.models.Container;
import com.skillstorm.models.Message;
import com.skillstorm.services.URLParserService;

@WebServlet(urlPatterns = "/containers/*")
public class ContainerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5832390794287951749L;
	
	ContainerDAO dao = new ContainerDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			
			// Try to convert to int and search by id
			int id = Integer.parseInt(param[1]);
			Container container = dao.findByParam(id);
		
			if (container == null) {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new Message("No container with that id")));
			} else {
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(container));
			}
			
		} catch (NumberFormatException e) {
			
			// Got a string instead, search by location
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			Container container = dao.findByParam(param[1]);
			
			if (container == null) {

				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new Message("No container with that id")));
			} else {
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(container));
			}
		} catch (Exception e) {
			List<Container> containers = dao.findAll();
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(containers));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Container container = mapper.readValue(reqBody, Container.class);
		System.out.println(container.toString());	
		container = dao.save(container);
		
		if (container == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Error creating container")));
		} else {
			resp.setStatus(201);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(container));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		Container container = mapper.readValue(reqBody, Container.class);

		container = dao.update(container);
		
		if (container == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new Message("Error updating container")));
		} else {
			resp.setStatus(200);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(container));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String[] param = urlService.extractParamFromURL(req.getPathInfo());
			
			// Get int from string for id
			int id = Integer.parseInt(param[1]);
			if (dao.delete(id)) {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Sucessfully deleted container with id " + id)));
			} else {
				resp.setStatus(400);
				resp.getWriter().print(mapper.writeValueAsString(new Message("Unable to delete container")));
			}

		} catch (Exception e) {
			
		}
	}
}