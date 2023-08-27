package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Address;
import model.User;

public class UserDAOImpl implements UserDAO {
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {}
	}

	private Connection getConnection() throws SQLException {
		Connection con = null;
		if (System.getProperty("RDS_HOSTNAME") != null) {
			try {
		      Class.forName("com.mysql.jdbc.Driver");
		      String userName = System.getProperty("RDS_USERNAME");
		      String password = System.getProperty("RDS_PASSWORD");
		      String hostname = System.getProperty("RDS_HOSTNAME");
		      String port = System.getProperty("RDS_PORT");
		      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + "db" + "?user=" + userName + "&password=" + password;
		      con = DriverManager.getConnection(jdbcUrl);
		      return con;
		    } catch (ClassNotFoundException e) { 
		    	e.printStackTrace();
		    }catch (SQLException e) { 
		    	e.printStackTrace();
		    }
		    return con;
		}else {
			//This is only used for development, when project is not deployed
			//hard coded connection
			try {
		      Class.forName("com.mysql.jdbc.Driver");
		      String userName = "root";
		      String password = "root1234";
		      String hostname = "awseb-e-bybgza4twa-stack-awsebrdsdatabase-57j2ooqi4tt8.cxocrl7z2rgw.us-east-2.rds.amazonaws.com";
		      String port = "3306";
		      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + "db" + "?user=" + userName + "&password=" + password;
		      con = DriverManager.getConnection(jdbcUrl);
		      return con;
			}catch (ClassNotFoundException e) { 
		    	System.out.println(e.toString());
		    }catch (SQLException e) { 
		    	e.printStackTrace();
		    }		      
		}
		return con;
	}
	
	private void closeConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> findAllUsers() {
		List<User> result = new ArrayList<User>();		
		String query = "SELECT * FROM User INNER JOIN Address ON User.addressID = Address.id;";
		queryUsers(query, result);
		return result;
	}

	@Override
	public List<User> findAllCustomers() {
		List<User> result = new ArrayList<User>();		
		String query = "SELECT * FROM User INNER JOIN Address ON User.addressID = Address.id WHERE isAdmin = 0;";
		queryUsers(query, result);
		return result;
	}

	@Override
	public List<User> findAllAdmins() {
		List<User> result = new ArrayList<User>();		
		String query = "SELECT * FROM User INNER JOIN Address ON User.addressID = Address.id WHERE isAdmin = 1;";
		queryUsers(query, result);
		return result;
	}
	
	@Override
	public User findUserById(int id) {
		List<User> result = new ArrayList<User>();		
		String query = "SELECT * FROM User INNER JOIN Address ON User.addressID = Address.id WHERE User.id ='" + id + "';";
		queryUsers(query, result);
		return result.isEmpty() ? null : result.get(0);
	}
	
	@Override
	public String signIn(String email, String password) {
		String result = "";
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM User WHERE email = '" + email + "';");
	
			// Check if user exists
			if (resultSet.next() == false) {
				result = "user does not exist";
			}						
			
			// Check if password is correct
			else if (!resultSet.getString("password").equals(password)) {
				result = "incorrect password";
			}
			
			// Check if regular user or admin		
			//changed to also include user id for easy user lookup in Login Servlet
			else {
				result = (resultSet.getInt("isAdmin") == 0) ? "customer" : "admin";
				result += " " + resultSet.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public void registerUser(User user) {	
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		int isAdmin = user.getIsAdmin();
		String email = user.getEmail();
		String password = user.getPassword();

		String streetAddress = user.getAddress().getStreetAddress();
		String province = user.getAddress().getProvince();
		String country = user.getAddress().getCountry();
		String postalCode = user.getAddress().getPostalCode();
		String phone = user.getAddress().getPhone();

		Connection connection = null;
		try {
			connection = getConnection();

			String addressSQL = "INSERT into Address (streetAddress, province, country, postalCode, phone) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement addressStatement = connection.prepareStatement(addressSQL, Statement.RETURN_GENERATED_KEYS);
			addressStatement.setString(1, streetAddress);
			addressStatement.setString(2, province);
			addressStatement.setString(3, country);
			addressStatement.setString(4, postalCode);
			addressStatement.setString(5, phone);
			addressStatement.execute();
			
			ResultSet addressKeys = addressStatement.getGeneratedKeys();
			int addressId = 0;
			if (addressKeys.next()) {
				addressId = addressKeys.getInt(1);
			}

			String userSQL = "INSERT into User (firstName, lastName, addressID, isAdmin, email, password) VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement userStatement = connection.prepareStatement(userSQL);
			userStatement.setString(1, firstName);
			userStatement.setString(2, lastName);
			userStatement.setInt(3, addressId);
			userStatement.setInt(4, isAdmin);
			userStatement.setString(5, email);
			userStatement.setString(6, password);
			userStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	@Override
	public void removeUser(User user) {
		int userId = user.getId();
		int addressId = user.getAddress().getId();

		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM User WHERE id = " + userId + ";");
			statement.executeUpdate("DELETE FROM Address WHERE id = " + addressId + ";");
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	@Override
	public void changeUserStatus(int id) {
		User user = findUserById(id);
		int value;
		
		if (user.getIsAdmin() == 0) {
			value = 1;
		} else {
			value = 0;
		}
		
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE User SET isAdmin = " + value + " WHERE id = " + id + ";");		

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	private void queryUsers(String query, List<User> result) {	
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String streetAddress = resultSet.getString("streetAddress");
				String province = resultSet.getString("province");
				String country = resultSet.getString("country");
				String postalCode = resultSet.getString("postalCode");
				String phone = resultSet.getString("phone");
				Address address = new Address(id, streetAddress, province, country, postalCode, phone);
				
				int userId = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				int isAdmin = resultSet.getInt("isAdmin");
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");
				
				User user = new User(userId, firstName, lastName, address, isAdmin, email, password);				
				result.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
