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
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Leah\\Downloads\\teamJIL.db");
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
	public List<Item> findAllItems() {
		List<Item> result = new ArrayList<Item>();		
		String query = "SELECT * FROM Item INNER JOIN Category, Brand ON Item.category = Category.id AND Item.brand = Brand.id;";
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
		String query = "SELECT * FROM Item INNER JOIN Category, Brand ON Item.category = Category.id AND Item.brand = Brand.id WHERE Category.name='" + category + "';";
		queryItems(query, result);
		return result;
	}

	@Override
	public List<Item> findItemsByBrand(String brand) {
		List<Item> result = new ArrayList<Item>();		
		String query = "SELECT * FROM Item INNER JOIN Category, Brand ON Item.category = Category.id AND Item.brand = Brand.id WHERE Brand.name='" + brand + "';";
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

				Item item = new Item(itemId, itemName, description, category, brand, price, quantity);				
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
