package edu.ycp.cs.cs496.TGOH.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs.cs496.TGOH.JSON.JSON;
import edu.ycp.cs.cs496.TGOH.temp.Courses;
import edu.ycp.cs.cs496.TGOH.temp.Registration;

public class RegisterForCourse {
	public boolean postRegisterRequest(Registration reg) throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
		return makePostRequest(reg);
	}
	
	public boolean makePostRequest(Registration reg) throws URISyntaxException, JsonGenerationException, JsonMappingException, IOException {
	HttpClient client = new DefaultHttpClient();
	
	// Construct URI
	URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/regforcourses/" , null, null);
	
	// Construct request
	HttpPost request = new HttpPost(uri);

	if(reg != null){
		Registration Reg = reg;

		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, Reg);
		
		// Add JSON object to request
		StringEntity reqEntity = new StringEntity(sw.toString());
		reqEntity.setContentType("application/json");
		request.setEntity(reqEntity);
			
		// Execute request
		HttpResponse response = client.execute(request);
		System.out.println(response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			return true;
		}else{
			return false;
		}
	}	
	
	return false;
	}

}
