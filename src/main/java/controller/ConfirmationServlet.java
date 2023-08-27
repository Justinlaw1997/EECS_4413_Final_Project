package controller;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Item;
import model.Order;
import model.User;
import dao.OrderDAO;
import dao.OrderDAOImpl;

/**
 * Servlet implementation class ConfimationServlet
 */
@WebServlet("/ConfirmationServlet")
public class ConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
	    HttpSession session = request.getSession();

		// Payment Service Algorithm - deny every 3rd request
		Integer counter = (Integer) session.getAttribute("count");
		counter = (counter == null)? 1 : counter + 1;
		session.setAttribute("count", counter);	
		
		if (counter % 3 == 0) {
			rd = request.getRequestDispatcher("jsp/CardFailed.jsp");
			rd.forward(request, response);
			
		} else {
			// Create a new Order
			User user = (User) session.getAttribute("user");
			System.out.println(user.toString());
			String date = new Date().toString();
			Cart cart = (Cart) session.getAttribute("cart");
			List<Item> items = cart.getItems();
			HashMap<Item, Integer> lineItems = new HashMap<Item, Integer>();
			
			// For each item, add it and the quantity purchased, then increment the totalPrice 
			int total = 0;
			for (Item item: items) {
				lineItems.put(item, item.getQuantityPurchased());
				total += item.getPrice() * item.getQuantityPurchased();
			}
			
			Order order = new Order();	
			order.setCustomer(user);
			order.setDateOfPurchase(date);
			order.setTotal(total);
			order.setItems(lineItems);
			
			// Save the new order to the database
			OrderDAO dao = new OrderDAOImpl();
			int orderId = dao.createOrder(order);
			order.setId(orderId);
			
			// Clear the cart
			cart.clear();
			
			// Forward the order to the success page
			request.setAttribute("order", order);
			rd = request.getRequestDispatcher("jsp/OrderSuccess.jsp");
			rd.forward(request, response);
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
