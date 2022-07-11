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
import com.skillstorm.daos.ArtistDAO;
import com.skillstorm.daos.MYSQLArtistsDAOImpl;
import com.skillstorm.models.Artist;
import com.skillstorm.models.NotFound;
import com.skillstorm.services.URLParserService;

@WebServlet(urlPatterns = "/artists/*")
public class ArtistServlet extends HttpServlet{

	/**
	 *  Server Lifecycle
	 *  
	 *  Init - Method called when the web server first creates our servlet
	 *  Service - Method is called when a request is made
	 *  Destroy - Method is called when web server is stopped
	 */
	
	@Override
	public void init() throws ServletException {
		// Runs code right as the servlet is created
		// You can establish any conenctions
		System.out.println("Servlet Created");
	}
	
	@Override
	public void destroy() {
		// If any connections were established in init, terminate here
		System.out.println("Servlet Destroyed");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Runs on every http requests to this servlet
		System.out.println("Servicing requests");
		super.service(req, resp); // Keep this line
	}
	
	// Filters are better
	
	private static final long serialVersionUID = 5795274365670879885L;

	ArtistDAO dao = new MYSQLArtistsDAOImpl();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();
	
	// Returns all artists
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int id = urlService.extractIdFromURL(req.getPathInfo());
			Artist artist = dao.findById(id);
			if (artist == null) {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString(new NotFound("No artist with provided id found")));
			} else {
				resp.setContentType("application/JSON");
				resp.getWriter().print(mapper.writeValueAsString(artist));
			}		
		} catch (Exception e) {
			// No id, fetch all artists instead
			List<Artist> artists = dao.findAll();
			System.out.println(artists);
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(artists));
		}			
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Adding a new artist");
		InputStream reqBody = req.getInputStream();
		System.out.println("Got input stream");
		Artist newArtist = mapper.readValue(reqBody, Artist.class);
		System.out.println("Mapped JSON to POJO");
		newArtist = dao.save(newArtist);
		System.out.println("Saved to database");
		if (newArtist == null) {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to create artists")));
		
		} else {
			resp.setContentType("application/JSON");
			resp.getWriter().print(mapper.writeValueAsString(newArtist));
			resp.setStatus(201);
		}
//		int id = Integer.parseInt(req.getParameter("artist-id"));
//		String name = (req.getParameter("artist-name"));
//		System.out.println(id);
//		System.out.println(name);
	}
		
}
