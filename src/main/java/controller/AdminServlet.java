package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.ItemDAO;
import dao.ItemDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import model.Brand;
import model.Category;
import model.Item;
import model.Order;
import model.User;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set up the DAOs
		OrderDAO orderDao = new OrderDAOImpl();
		ItemDAO itemDao = new ItemDAOImpl();
		UserDAO userDao = new UserDAOImpl();
		
		List<Item> items;
		List<Category> categories;
		List<Brand> brands;
		List<User> users;
		List<Order> orders;		

		// TODO: Move this logic somewhere in the registration servlet
		request.getSession().setAttribute("user", userDao.findUserById(1));
		
		// Select which JSP View to Display
		RequestDispatcher rd;
		String selection = request.getParameter("selection");
		
		if (selection == null) {
	    	rd = request.getRequestDispatcher("jsp/AdminView.jsp");
	    	rd.forward(request, response);
	    	
		} else if (selection.equals("Manage Items")) {
			// Fetch all of the items
			items = itemDao.findAllItems();
			request.setAttribute("items", items);
	    	rd = request.getRequestDispatcher("jsp/AdminItemView.jsp");
	    	rd.forward(request, response);
			
		} else if (selection.equals("Manage Orders")) {
			// Fetch All Items, Categories, Brands, and Users
			items = itemDao.findAllItems();
			categories = itemDao.findAllCategories();
			brands = itemDao.findAllBrands();
			users = userDao.findAllUsers();
			
			// Filter the Orders
			String filterOrders = request.getParameter("filterOrders");
			if (filterOrders == null) {
				orders = orderDao.findAllOrders();
				
			} else if (filterOrders.equals("Filter by Customer")) {
				int customerId = Integer.parseInt(request.getParameter("customer"));
				orders = orderDao.findAllOrdersByCustomer(customerId);

			} else if (filterOrders.equals("Filter by Item")) {
				String item = request.getParameter("item");
				orders = orderDao.findAllOrdersByItem(item);

			} else if (filterOrders.equals("Filter by Brand")) {
				String brand = request.getParameter("brand");
				orders = orderDao.findAllOrdersByBrand(brand);

			} else if (filterOrders.equals("Filter by Category")) {
				String category = request.getParameter("category");
				orders = orderDao.findAllOrdersByCategory(category);
				
			} else if (filterOrders.equals("Filter by Date")) {
				String date = request.getParameter("date");
				orders = orderDao.findAllOrdersByDate(date);
				
			} else {
				orders = orderDao.findAllOrders();
			}	
			 
			// Set request attributes
			request.setAttribute("orders", orders);
			request.setAttribute("items", items);
			request.setAttribute("categories", categories);
			request.setAttribute("brands", brands);
			request.setAttribute("users", users);
	    	rd = request.getRequestDispatcher("jsp/AdminOrderView.jsp");
	    	rd.forward(request, response);
	    	
		} else if (selection.equals("Manage Users")) {
			// Filter the Users
			String filterUsers = request.getParameter("filterUsers");
			if (filterUsers == null) {
				users = userDao.findAllUsers();
				
			} else if (filterUsers.equals("View Customers")) {
				users = userDao.findAllCustomers();

			} else if (filterUsers.equals("View Admins")) {
				users = userDao.findAllAdmins();

			} else {
				users = userDao.findAllUsers();
			}	
			
			// Set request attributes
			request.setAttribute("users", users);
	    	rd = request.getRequestDispatcher("jsp/AdminUserView.jsp");
	    	rd.forward(request, response);
	    	
		} else if (selection.equals("Log Out")) {
			request.getSession().setAttribute("user", null);
	    	rd = request.getRequestDispatcher("index.html");
	    	rd.forward(request, response);
	    	
		} else {
	    	rd = request.getRequestDispatcher("jsp/AdminView.jsp");
	    	rd.forward(request, response);
		}	   			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
