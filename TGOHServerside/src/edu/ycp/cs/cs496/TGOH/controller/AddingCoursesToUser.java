package edu.ycp.cs.cs496.TGOH.controller;

import edu.ycp.cs.cs496.TGOH.pesist.Database;
import edu.ycp.cs.cs496.TGOH.pesist.IDatabase;
import edu.ycp.cs.cs496.TGOH.temp.Courses;
import edu.ycp.cs.cs496.TGOH.temp.Registration;

public class AddingCoursesToUser {
	public void addingRegistrationToUser(Registration reg){
		IDatabase db = Database.getInstance();
		db.registerUserForCourse(reg);
	}
}
