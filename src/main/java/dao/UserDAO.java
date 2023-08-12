package dao;

import java.util.List;

import model.User;

public interface UserDAO {
	
	public List<User> findAllUsers();
	
	public List<User> findAllCustomers();
	
	public List<User> findAllAdmins();
	
	public String signIn(String email, String password);
	
	public void registerUser(User user);
	
	public void removeUser(User user);

}
