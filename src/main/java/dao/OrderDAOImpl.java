package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import model.Item;
import model.Order;
import model.User;

public class OrderDAOImpl implements OrderDAO {
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {}
	}

	private Connection getConnection() throws SQLException {
		Connection con = null;
	      Logger logger= Logger.getLogger(OrderDAOImpl.class.getName());
		if (System.getProperty("RDS_HOSTNAME") != null) {
			try {
		      Class.forName("com.mysql.jdbc.Driver");
		      String userName = System.getProperty("RDS_USERNAME");
		      String password = System.getProperty("RDS_PASSWORD");
		      String hostname = System.getProperty("RDS_HOSTNAME");
		      String port = System.getProperty("RDS_PORT");
		      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + "db" + "?user=" + userName + "&password=" + password;
		      logger.info("Getting remote connection with connection string from environment variables.");
		      con = DriverManager.getConnection(jdbcUrl);
		      logger.info("Remote connection successful.");
		      return con;
		    } catch (ClassNotFoundException e) { 
		    	logger.warning(e.toString());
		    }catch (SQLException e) { 
		    	logger.warning(e.toString());
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
		      logger.info("Remote connection successful.");
		      return con;
			}catch (ClassNotFoundException e) { 
		    	logger.warning(e.toString());
		    }catch (SQLException e) { 
		    	logger.warning(e.toString());
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
	public Order findOrderById(int id) {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM Orders WHERE id = " + id + ";";
		queryOrders(query, result);
		return result.isEmpty()? null : result.get(0);
	}

	@Override
	public List<Order> findAllOrders() {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM Orders;";
		queryOrders(query, result);
		return result;
	}

	@Override
	public List<Order> findAllOrdersByCustomer(int customerId) {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM Orders WHERE customerID = " + customerId + ";";
		queryOrders(query, result);
		return result;
	}

	@Override
	public List<Order> findAllOrdersByItem(String itemId) {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM ItemOrder INNER JOIN Item ON ItemOrder.itemId = Item.itemID INNER JOIN Orders " + 
				"ON ItemOrder.orderId = Orders.id WHERE Item.itemID = '" + itemId + "' GROUP BY Orders.id;";
		queryOrders(query, result);
		return result;	
	}

	@Override
	public List<Order> findAllOrdersByBrand(String brand) {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM ItemOrder INNER JOIN Item ON ItemOrder.itemId = Item.itemID INNER JOIN Orders ON ItemOrder.orderId = " +
				"Orders.id INNER JOIN Brand ON Item.brand = Brand.id WHERE Brand.name = \"" + brand + "\" GROUP BY Orders.id";
		queryOrders(query, result);
		return result;
	}
	
	@Override
	public List<Order> findAllOrdersByCategory(String category) {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM ItemOrder INNER JOIN Item ON ItemOrder.itemId = Item.itemID INNER JOIN Orders ON ItemOrder.orderId = " +
				"Orders.id INNER JOIN Category ON Item.category = Category.id WHERE Category.name = \"" + category + "\" GROUP BY Orders.id;";
		queryOrders(query, result);
		return result;
	}

	@Override
	public List<Order> findAllOrdersByDate(String date) {
		List<Order> result = new ArrayList<Order>();
		String query = "SELECT * FROM Orders WHERE dateOfPurchase = \"" + date + "\";";
		queryOrders(query, result);
		return result;
	}
	
	@Override
	public void createOrder(Order order) {
		Connection connection = null;
		try {
			connection = getConnection();
			
			String orderSQL = "INSERT into Orders VALUES (?, ?, ?, ?)";
			PreparedStatement orderStatement = connection.prepareStatement(orderSQL);
			orderStatement.setInt(1, order.getId());
			orderStatement.setInt(2, order.getCustomer().getId());
			orderStatement.setString(3, order.getDateOfPurchase());
			orderStatement.setInt(4, order.getTotal());
			orderStatement.execute();
			
			for(Item item: order.getItems()) {
				String itemsSQL = "INSERT into ItemOrder VALUES (?, ?)";
				PreparedStatement itemStatement = connection.prepareStatement(itemsSQL);
				itemStatement.setString(1, item.getItemID());
				itemStatement.setInt(2, order.getId());
				itemStatement.execute();
				
				// Reduce quantity of purchased items
				int quantity = item.getQuantity();
				quantity -= 1;
				Statement update = connection.createStatement();
				update.executeUpdate("UPDATE Item SET quantity =" + quantity + " WHERE itemId = '" + item.getItemID() + "';");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	@Override
	public void deleteOrder(int id) {
		Order order = findOrderById(id);
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM Orders WHERE id = " + id + ";");
			statement.executeUpdate("DELETE FROM ItemOrder WHERE orderId = " + id + ";");

			// Re-stock cancelled items
			for(Item item: order.getItems()) {				
				int quantity = item.getQuantity();
				quantity += 1;
				Statement update = connection.createStatement();
				update.executeUpdate("UPDATE Item SET quantity =" + quantity + " WHERE itemId = '" + item.getItemID() + "';");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
	
	private void queryOrders(String query, List<Order> result) {	
		UserDAOImpl customer = new UserDAOImpl();
		ItemDAOImpl itemList = new ItemDAOImpl();
		
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				int orderId = resultSet.getInt("id");
				User user = customer.findUserById(resultSet.getInt("customerID"));
				String date = resultSet.getString("dateOfPurchase");
				int total = resultSet.getInt("total");
				List<Item> items = new ArrayList<Item>();

				// Get the list of items associated with the order
				Statement itemStatement = connection.createStatement();
				ResultSet itemResultSet = itemStatement.executeQuery("SELECT * FROM ItemOrder WHERE orderId = '" + orderId + "';");
				while(itemResultSet.next()) {
					Item item = itemList.findItemById(itemResultSet.getString("itemId"));
					items.add(item);
				}
						
				Order order = new Order(orderId, user, date, total, items);				
				result.add(order);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}
