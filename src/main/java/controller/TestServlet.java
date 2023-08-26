package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ItemDAOImpl;
import dao.OrderDAOImpl;
import dao.UserDAOImpl;
import model.Address;
import model.Brand;
import model.Category;
import model.Item;
import model.Order;
import model.User;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TESTING ITEM DAO IMPLEMENTATION
		ItemDAOImpl itemList = new ItemDAOImpl();
		
		System.out.println("-------------------FIND SINGLE ITEM");
		Item itemYes = itemList.findItemById("e001");
		System.out.println(itemYes.toString());
		
		System.out.println("-------------------ITEM DOESN'T EXIST");
		Item itemNo = itemList.findItemById("fake id");
		System.out.println(itemNo);
		
		System.out.println("-------------------FIND ALL ITEMS");
		List<Item> items1 = itemList.findAllItems();
		for(Item item: items1) {
			System.out.println(item.toString());
		}
		
		System.out.println("-------------------SORT PRICE ASC");
		List<Item> items2 = itemList.SortItemsByAscendingPrice();
		for(Item item: items2) {
			System.out.println(item.getPrice());
		}
		
		System.out.println("-------------------SORT PRICE DESC");
		List<Item> items3 = itemList.SortItemsByDescendingPrice();
		for(Item item: items3) {
			System.out.println(item.getPrice());
		}
		
		System.out.println("-------------------SORT ALPH");
		List<Item> items4 = itemList.SortItemsByAlphabeticalOrder();
		for(Item item: items4) {
			System.out.println(item.getName());
		}
		
		System.out.println("-------------------FIND BY CATEGORY");
		List<Item> items5 = itemList.findItemsByCategory("video game");
		for(Item item: items5) {
			System.out.println(item.getName());
		}
		
		System.out.println("-------------------FIND BY BRAND");
		List<Item> items6 = itemList.findItemsByBrand("Dell");
		for(Item item: items6) {
			System.out.println(item.getName());
		}
		
		System.out.println("-------------------FIND ALL CATEGORIES");
		List<Category> categories = itemList.findAllCategories();
		for(Category category: categories) {
			System.out.println(category.toString());
		}
		
		System.out.println("-------------------FIND ALL BRANDS");
		List<Brand> brands = itemList.findAllBrands();
		for(Brand brand: brands) {
			System.out.println(brand.toString());
		}
		
		// TESTING USER DAO IMPLEMENTATION
		UserDAOImpl userList = new UserDAOImpl();
		
		System.out.println("-------------------FIND ALL USERS");
		List<User> users1 = userList.findAllUsers();
		for(User user: users1) {
			System.out.println(user.toString());
		}
		
		System.out.println("-------------------FIND CUSTOMERS");
		List<User> users2 = userList.findAllCustomers();
		for(User user: users2) {
			System.out.println(user.toString());
		}
		
		System.out.println("-------------------FIND ADMINS");
		List<User> users3 = userList.findAllAdmins();
		for(User user: users3) {
			System.out.println(user.toString());
		}	
		
		System.out.println("-------------------FIND SINGLE USER");
		User userYes = userList.findUserById(3);
		System.out.println(userYes.toString());
		
		System.out.println("-------------------USER DOESN'T EXIST");
		User userNo = userList.findUserById(15);
		System.out.println(userNo);	
		
		System.out.println("-------------------ADD USER");
		Address address = new Address(99, "street", "province", "country", "code", "555");
		User newUser = new User(99, "John", "Doe", address, 0, "test@email.com", "password");
		userList.registerUser(newUser);
		users1 = userList.findAllUsers();
		for(User user: users1) {
			System.out.println(user.toString());
		}			
		
		System.out.println("-------------------REMOVE USER");
		userList.removeUser(newUser);
		users1 = userList.findAllUsers();
		for(User user: users1) {
			System.out.println(user.toString());
		}	
		
		System.out.println("-------------------SIGN IN RESULTS");
		System.out.println(userList.signIn("'fakeuser'", "iamironman"));
		System.out.println(userList.signIn("'tonystark@yahoo.ca'", "fakepass"));
		System.out.println(userList.signIn("'dk@yahoo.ca'", "bananas4ever"));
		System.out.println(userList.signIn("'bugs@gmail.com'", "whatsupdoc"));
		
		// TESTING ORDER DAO IMPLEMENTATION
		OrderDAOImpl orderList = new OrderDAOImpl();
		
		System.out.println("-------------------FIND SINGLE ORDER");
		Order singleOrder = orderList.findOrderById(5);
		System.out.println(singleOrder.toString());
		
		System.out.println("-------------------FIND ALL ORDERS");
		List<Order> orders = orderList.findAllOrders();
		for(Order order: orders) {
			System.out.println(order.toString());
		}
		
		System.out.println("-------------------ORDERS BY CUSTOMER");
		List<Order> orders2 = orderList.findAllOrdersByCustomer(3);
		for(Order order: orders2) {
			System.out.println(order.toString());
		}
		
		System.out.println("-------------------ORDERS BY ITEM");
		List<Order> orders3 = orderList.findAllOrdersByItem("e001");
		for(Order order: orders3) {
			System.out.println(order.toString());
		}
		
		System.out.println("-------------------ORDERS BY BRAND");
		List<Order> orders4 = orderList.findAllOrdersByBrand("Apple");
		for(Order order: orders4) {
			System.out.println(order.toString());
		}
		
		System.out.println("-------------------ORDERS BY CATEGORY");
		List<Order> orders5 = orderList.findAllOrdersByCategory("book");
		for(Order order: orders5) {
			System.out.println(order.toString());
		}
		
		System.out.println("-------------------ORDERS BY DATE");
		List<Order> orders6 = orderList.findAllOrdersByDate("06-04-2023");
		for(Order order: orders6) {
			System.out.println(order.toString());
		}
		
		System.out.println("-------------------CREATE AN ORDER");
		User cust = userList.findUserById(3);
		Item item1 = itemList.findItemById("e002");
		Item item2 = itemList.findItemById("b004");
		List<Item> items = new ArrayList<Item>();
		items.add(item1);
		items.add(item2);
		Order newOrder = new Order(99, cust, "08-08-2023", 40, items);
		orderList.createOrder(newOrder);
		orders = orderList.findAllOrders();
		for(Order order: orders) {
			System.out.println(order.toString());
		}
				
		System.out.println("-------------------DELETE AN ORDER");
		orderList.deleteOrder(99);
		orders = orderList.findAllOrders();
		for(Order order: orders) {
			System.out.println(order.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
