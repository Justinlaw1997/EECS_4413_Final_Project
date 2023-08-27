package dao;

import java.util.List;

import model.User;

public interface UserDAO {
	
	public List<User> findAllUsers();
	
	public List<User> findAllCustomers();
	
	public List<User> findAllAdmins();
	
	public User findUserById(int id);
		
	public String signIn(String email, String password);
	
	public int registerUser(User user);
	
	public void removeUser(User user);
	
	public void changeUserStatus(int id);

}
