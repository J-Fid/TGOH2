package edu.ycp.cs.cs496.TGOH.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.cs496.TGOH.JSON.JSON;
import edu.ycp.cs.cs496.TGOH.User.User;
import edu.ycp.cs.cs496.TGOH.controller.AddController;
import edu.ycp.cs.cs496.TGOH.controller.GetController;

public class Userpage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("text/plain");
			resp.getWriter().println("Getting entire inventory not supported yet");
			return;
		}
		
		// Get the item name
		if (pathInfo.startsWith("/")) 
			pathInfo = pathInfo.substring(1);

		// Use a GetItemByName controller to find the item in the database
		GetController controller = new GetController();
		User user = controller.getUser(pathInfo);
		
		if (user == null) {
			// No such item, so return a NOT FOUND response
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			resp.getWriter().println("No such item: " + pathInfo);
			return;
		}
		
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), user);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User user = JSON.getObjectMapper().readValue(req.getReader(), User.class);
		
		// Use a GetItemByName controller to find the item in the database
		AddController controller = new AddController();
		controller.addUser(user);
		
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// writing the operation out.
		JSON.getObjectMapper().writeValue(resp.getWriter(), user);
	}
}
