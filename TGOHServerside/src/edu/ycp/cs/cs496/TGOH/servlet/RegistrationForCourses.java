package edu.ycp.cs.cs496.TGOH.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs.cs496.TGOH.JSON.JSON;
import edu.ycp.cs.cs496.TGOH.controller.AcceptingUserforCourse;
import edu.ycp.cs.cs496.TGOH.controller.AddingCoursesToUser;
import edu.ycp.cs.cs496.TGOH.controller.GetUserController;
import edu.ycp.cs.cs496.TGOH.controller.RemovingAUserFromCourse;
import edu.ycp.cs.cs496.TGOH.controller.findUserForCourse;
import edu.ycp.cs.cs496.TGOH.controller.getAllCourses;
import edu.ycp.cs.cs496.TGOH.controller.gettingACourse;
import edu.ycp.cs.cs496.TGOH.temp.Courses;
import edu.ycp.cs.cs496.TGOH.temp.Registration;
import edu.ycp.cs.cs496.TGOH.temp.User;

public class RegistrationForCourses extends HttpServlet{
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			getAllCourses gt = new getAllCourses();
			List<Courses> cs = gt.getAllcourses();
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), cs);
			
			return;
		}
		
		// Get the item name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		String user = pathInfo.substring(0, pathInfo.indexOf('/'));
		
		if (pathInfo.contains("/")){
			pathInfo = pathInfo.substring(pathInfo.indexOf('/')+1, pathInfo.length());
		}
		int courseId = Integer.parseInt(pathInfo);
		// Use a GetItemByName controller to find the item in the database
		GetUserController con = new GetUserController();
		User userId = con.getUser(user);
		
		gettingACourse cont = new gettingACourse();
		Courses course = cont.getCourse(courseId);
		
		findUserForCourse controller = new findUserForCourse();
		Registration reg = controller.findUserforCourse(userId, course);
		
		if (reg == null) {
			// No such item, so return a NOT FOUND response
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			resp.getWriter().println("Not a course: " + pathInfo);
			return;
		}
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), reg);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Registration reg = JSON.getObjectMapper().readValue(req.getReader(), Registration.class);
		// Use a GetUser controller to find the item in the database
		AddingCoursesToUser con = new AddingCoursesToUser();
		con.addingRegistrationToUser(reg.getUserId(), reg.getCourseId());
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// writing the operation out.
		JSON.getObjectMapper().writeValue(resp.getWriter(), reg);
	}

	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Registration reg = JSON.getObjectMapper().readValue(req.getReader(), Registration.class);
		
		RemovingAUserFromCourse con = new RemovingAUserFromCourse();
		con.RemovingUserFromCourse(reg.getUserId(), reg.getCourseId());
		
		// send response
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");	
		
		gettingACourse controller = new gettingACourse();
		
		JSON.getObjectMapper().writeValue(resp.getWriter(), controller.getCourse(reg.getId()));
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, JsonGenerationException, JsonMappingException {
		
		String pathInfo = req.getPathInfo();
		
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.setContentType("application/json");
			return;
		}else{
			// Get the item name
			if (pathInfo.startsWith("/")){
				pathInfo = pathInfo.substring(1);
			}
			String user = pathInfo.substring(0, pathInfo.indexOf('/'));
			
			if (pathInfo.contains("/")){
				pathInfo = pathInfo.substring(pathInfo.indexOf('/')+1, pathInfo.length());
			}
			int courseId = Integer.parseInt(pathInfo);
			// Use a GetItemByName controller to find the item in the database
			GetUserController con = new GetUserController();
			User userId = con.getUser(user);
			
			gettingACourse cont = new gettingACourse();
			Courses course = cont.getCourse(courseId);
			
			AcceptingUserforCourse controller = new AcceptingUserforCourse();
			Registration reg = controller.acceptingUserforCourse(userId, course);
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// writing the operation out.
			JSON.getObjectMapper().writeValue(resp.getWriter(), reg);
		}
	}
}
