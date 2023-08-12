package dao;

import java.util.List;

import model.Brand;
import model.Category;
import model.Item;

public interface ItemDAO {
	
	public Item findItemById(String id);

	public List<Item> findAllItems();
	
	public List<Item> SortItemsByAscendingPrice();
	
	public List<Item> SortItemsByDescendingPrice();
	
	public List<Item> SortItemsByAlphabeticalOrder();
	
	public List<Item> findItemsByCategory(String category);
	
	public List<Item> findItemsByBrand(String brand);

	public List<Category> findAllCategories();

	public List<Brand> findAllBrands();

}
