package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.naming.*;


import org.apache.catalina.Context;
import org.apache.tomcat.jdbc.pool.DataSource;

import model.Brand;
import model.Category;
import model.Item;
import java.util.logging.*;

public class ItemDAOImpl implements ItemDAO {
	
	private Connection getConnection() throws SQLException {
		Connection con = null;
	      Logger logger= Logger.getLogger(ItemDAOImpl.class.getName());
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
	public Item findItemById(String id) {
		List<Item> result = new ArrayList<Item>();		
		String query = "SELECT * FROM Item INNER JOIN Category ON Item.category = Category.id INNER JOIN Brand ON Item.brand = Brand.id " + 
				"WHERE Item.itemID = '" + id + "';";
		queryItems(query, result);;
		return result.isEmpty()? null : result.get(0);
	}
	
	@Override
	public List<Item> findAllItems() {
		List<Item> result = new ArrayList<Item>();		
		String query = "SELECT * FROM Item INNER JOIN Category ON Item.category = Category.id INNER JOIN Brand ON Item.brand = Brand.id";
		queryItems(query, result);;
		return result;
	}

	@Override
	public List<Item> SortItemsByAscendingPrice() {
		List<Item> result = new ArrayList<Item>();		
		result = findAllItems();
		Collections.sort(result, new ItemPriceAscComparator());
		return result;
	}
		
	@Override
	public List<Item> SortItemsByDescendingPrice() {
		List<Item> result = new ArrayList<Item>();		
		result = findAllItems();
		Collections.sort(result, new ItemPriceDesComparator());
		return result;
	}

	@Override
	public List<Item> SortItemsByAlphabeticalOrder() {
		List<Item> result = new ArrayList<Item>();		
		result = findAllItems();
		Collections.sort(result, new ItemNameComparator());
		return result;
	}

	@Override
	public List<Item> findItemsByCategory(String category) {
		List<Item> result = new ArrayList<Item>();		
		String query = "SELECT * FROM Item INNER JOIN Category ON Item.category = Category.id INNER JOIN Brand ON Item.brand = Brand.id WHERE Category.name='" + category + "';";
		queryItems(query, result);
		return result;
	}

	@Override
	public List<Item> findItemsByBrand(String brand) {
		List<Item> result = new ArrayList<Item>();		
		String query = "SELECT * FROM Item INNER JOIN Category ON Item.category = Category.id INNER JOIN Brand ON Item.brand = Brand.id WHERE Brand.name='" + brand + "';";
		queryItems(query, result);
		return result;
	}
	
	@Override
	public List<Category> findAllCategories() {
		List<Category> result = new ArrayList<Category>();	
		String query = "SELECT * FROM Category";
		
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {			
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Category category = new Category(id, name);				
				result.add(category);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return result;
	}

	@Override
	public List<Brand> findAllBrands() {
		List<Brand> result = new ArrayList<Brand>();	
		String query = "SELECT * FROM Brand";
		
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {			
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Brand brand = new Brand(id, name);				
				result.add(brand);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return result;
	}

	private void queryItems(String query, List<Item> result) {	
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {		
				int categoryId = resultSet.getInt(8);
				String categoryName = resultSet.getString(9);
				Category category = new Category(categoryId, categoryName);
				
				int brandId = resultSet.getInt(10);
				String brandName = resultSet.getString(11);
				Brand brand = new Brand(brandId, brandName);
				
				String itemId = resultSet.getString("itemID");
				String itemName = resultSet.getString("name");
				String description = resultSet.getString("description");
				int price = resultSet.getInt("price");
				int quantity = resultSet.getInt("quantity");
				String image = "images/" + itemId + ".jpg";

				Item item = new Item(itemId, itemName, description, category, brand, price, quantity, image);				
				result.add(item);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}
}

class ItemPriceAscComparator implements Comparator<Item> {
	@Override
	public int compare(Item a, Item b) {
		return a.getPrice() - b.getPrice();
	}
}

class ItemPriceDesComparator implements Comparator<Item> {
	@Override
	public int compare(Item a, Item b) {
		return b.getPrice() - a.getPrice();
	}
}

class ItemNameComparator implements Comparator<Item> {
	@Override
	public int compare(Item a, Item b) {
		return a.getName().compareToIgnoreCase(b.getName());
	}
}
