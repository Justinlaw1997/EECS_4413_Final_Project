package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		      Class.forName("org.postgresql.Driver");
		      String dbName = System.getProperty("RDS_DB_NAME");
		      String userName = System.getProperty("RDS_USERNAME");
		      String password = System.getProperty("RDS_PASSWORD");
		      String hostname = System.getProperty("RDS_HOSTNAME");
		      String port = System.getProperty("RDS_PORT");
		      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
		      System.out.println("Getting remote connection with connection string from environment variables.");
		      con = DriverManager.getConnection(jdbcUrl);
		      System.out.println("Remote connection successful.");
		      return con;
		    }
		    catch (ClassNotFoundException e) { e.printStackTrace();}
		    catch (SQLException e) { e.printStackTrace();}
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
		return result.isEmpty()? null : result.get(0);
	}
	
	@Override
	public String signIn(String email, String password) {
		String result = "";
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM User WHERE email = " + email + ";");
	
			// Check if user exists
			if (resultSet.next() == false) {
				result = "user does not exist";
			}						
			
			// Check if password is correct
			else if (!resultSet.getString("password").equals(password)) {
				result = "incorrect password";
			}
			
			// Check if regular user or admin			
			else {
				result = (resultSet.getInt("isAdmin") == 0) ? "customer" : "admin";
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
		int userId = user.getId();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		int isAdmin = user.getIsAdmin();
		String email = user.getEmail();
		String password = user.getPassword();
		
		int addressId = user.getAddress().getId();
		String streetAddress = user.getAddress().getStreetAddress();
		String province = user.getAddress().getProvince();
		String country = user.getAddress().getCountry();
		String postalCode = user.getAddress().getPostalCode();
		String phone = user.getAddress().getPhone();
				
		Connection connection = null;
		try {
			connection = getConnection();

			String addressSQL = "INSERT into Address VALUES (?, ?, ?, ?, ?, ?);";
			PreparedStatement addressStatement = connection.prepareStatement(addressSQL);
			addressStatement.setInt(1, addressId);
			addressStatement.setString(2, streetAddress);
			addressStatement.setString(3, province);
			addressStatement.setString(4, country);
			addressStatement.setString(5, postalCode);
			addressStatement.setString(6, phone);
			addressStatement.execute();

			String userSQL = "INSERT into User VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement userStatement = connection.prepareStatement(userSQL);
			userStatement.setInt(1, userId);
			userStatement.setString(2, firstName);
			userStatement.setString(3, lastName);
			userStatement.setInt(4, addressId);
			userStatement.setInt(5, isAdmin);
			userStatement.setString(6, email);
			userStatement.setString(7, password);
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

	private void queryUsers(String query, List<User> result) {	
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {	
				int addressId = resultSet.getInt("addressId");
				String streetAddress = resultSet.getString("streetAddress");
				String province = resultSet.getString("province");
				String country = resultSet.getString("country");
				String postalCode = resultSet.getString("postalCode");
				String phone = resultSet.getString("phone");
				Address address = new Address(addressId, streetAddress, province, country, postalCode, phone);
				
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
