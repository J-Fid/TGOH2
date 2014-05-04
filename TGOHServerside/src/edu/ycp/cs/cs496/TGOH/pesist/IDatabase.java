package edu.ycp.cs.cs496.TGOH.pesist;

import java.util.List;

import edu.ycp.cs.cs496.TGOH.temp.Courses;
import edu.ycp.cs.cs496.TGOH.temp.Notification;
import edu.ycp.cs.cs496.TGOH.temp.Registration;
import edu.ycp.cs.cs496.TGOH.temp.User;

public interface IDatabase {
/**
 * Database persistence methods
 * @return 
 */
	public void addUser(User user);
	
	public User getUser(String Username);
	
	public boolean deleteUser(User user);
	
	public Courses getCourse(int coursename);
	
	public void addCourse(Courses course);
	
	public void deleteCourse(int Coursename);
	
	public Registration findUserForCourse(User user, Courses course);
	
	public Courses[] getCoursefromUser(int user);
	
	public List<Courses> getAllCourse();

	public void RemovingUserFromCourse(User user, Courses course);

	public Registration registerUserForCourse(int user, int course);
	
	public Registration AcceptingUserforCourse(User user, Courses course);
	
	public User[] getPendingUserforCourse(int course);

	public void removeNotification(int id);
	
	public Notification addNotification(int courseId, String text);
	
	public List<Notification> getNotificationForCourse(int courseId);
	
	public Notification getNotification(int id);
}
