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

import model.Brand;
import model.Category;
import model.Item;

public class ItemDAOImpl implements ItemDAO {
	
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
		    	e.printStackTrace();
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
				Category category = new Category();
				category.setId(id);
				category.setName(name);
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
				Brand brand = new Brand();
				brand.setId(id);
				brand.setName(name);
				result.add(brand);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		
		return result;
	}
	
	@Override
	public void updateQuantity(String itemID, int quantity) {
		String query = "UPDATE Item SET quantity = " + quantity + " WHERE itemID = '" + itemID + "';";
		
		Connection connection = null;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}	
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
				Category category = new Category();
				category.setId(categoryId);
				category.setName(categoryName);
				
				int brandId = resultSet.getInt(10);
				String brandName = resultSet.getString(11);
				Brand brand = new Brand();
				brand.setId(brandId);
				brand.setName(brandName);				

				String itemId = resultSet.getString("itemID");
				String itemName = resultSet.getString("name");
				String description = resultSet.getString("description");
				int price = resultSet.getInt("price");
				int quantityStocked = resultSet.getInt("quantity");
				int quantityPurchased = 0;
				String image = "images/" + itemId + ".jpg";

				Item item = new Item();		
				item.setItemID(itemId);
				item.setName(itemName);
				item.setDescription(description);
				item.setImage(description);
				item.setCategory(category);
				item.setBrand(brand);
				item.setPrice(price);
				item.setQuantityStocked(quantityStocked);
				item.setQuantityPurchased(quantityPurchased);
				item.setImage(image);

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
