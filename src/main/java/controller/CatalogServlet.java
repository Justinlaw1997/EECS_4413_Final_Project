package controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemDAOImpl;
import dao.UserDAOImpl;
import model.Address;
import model.Brand;
import model.Category;
import model.Item;
import model.User;

/**
 * Servlet implementation class CatalogServlet
 */
@WebServlet("/CatalogServlet")
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	// get author selections from index.html, and search for the books, get a arraylist of books
		
		ItemDAOImpl itemList = new ItemDAOImpl();
		
		// TESTING ITEM DAO IMPLEMENTATION (TO BE REMOVED)
		List<Item> items1 = itemList.findAllItems();
		System.out.println("-------------------FIND ALL ITEMS");
		for(Item item: items1) {
			System.out.println(item.toString());
		}
		List<Item> items2 = itemList.SortItemsByAscendingPrice();
		System.out.println("-------------------SORT PRICE ASC");
		for(Item item: items2) {
			System.out.println(item.getPrice());
		}
		List<Item> items3 = itemList.SortItemsByDescendingPrice();
		System.out.println("-------------------SORT PRICE DESC");
		for(Item item: items3) {
			System.out.println(item.getPrice());
		}
		List<Item> items4 = itemList.SortItemsByAlphabeticalOrder();
		System.out.println("-------------------SORT ALPH");
		for(Item item: items4) {
			System.out.println(item.getName());
		}
		List<Item> items5 = itemList.findItemsByCategory("video game");
		System.out.println("-------------------FIND BY CATEGORY");
		for(Item item: items5) {
			System.out.println(item.getName());
		}
		List<Item> items6 = itemList.findItemsByBrand("Dell");
		System.out.println("-------------------FIND BY BRAND");
		for(Item item: items6) {
			System.out.println(item.getName());
		}
		List<Category> categories = itemList.findAllCategories();
		System.out.println("-------------------FIND ALL CATEGORIES");
		for(Category category: categories) {
			System.out.println(category.toString());
		}
		List<Brand> brands = itemList.findAllBrands();
		System.out.println("-------------------FIND ALL BRANDS");
		for(Brand brand: brands) {
			System.out.println(brand.toString());
		}
		
		// TESTING USER DAO IMPLEMENTATION (TO BE REMOVED)
		UserDAOImpl userList = new UserDAOImpl();
		List<User> users1 = userList.findAllUsers();
		System.out.println("-------------------FIND ALL USERS");
		for(User user: users1) {
			System.out.println(user.toString());
		}
		List<User> users2 = userList.findAllCustomers();
		System.out.println("-------------------FIND CUSTOMERS");
		for(User user: users2) {
			System.out.println(user.toString());
		}
		List<User> users3 = userList.findAllAdmins();
		System.out.println("-------------------FIND ADMINS");
		for(User user: users3) {
			System.out.println(user.toString());
		}
		Address address = new Address(99, "street", "province", "country", "code", "555");
		User newUser = new User(99, "John", "Doe", address, 0, "test@email.com", "password");
		userList.registerUser(newUser);
		users1 = userList.findAllUsers();
		System.out.println("-------------------ADD USER");
		for(User user: users1) {
			System.out.println(user.toString());
		}			
		userList.removeUser(newUser);
		users1 = userList.findAllUsers();
		System.out.println("-------------------REMOVE USER");
		for(User user: users1) {
			System.out.println(user.toString());
		}	
		System.out.println("-------------------SIGN IN RESULTS");
		System.out.println(userList.signIn("'fakeuser'", "iamironman"));
		System.out.println(userList.signIn("'tonystark@yahoo.ca'", "fakepass"));
		System.out.println(userList.signIn("'dk@yahoo.ca'", "bananas4ever"));
		System.out.println(userList.signIn("'bugs@gmail.com'", "whatsupdoc"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
