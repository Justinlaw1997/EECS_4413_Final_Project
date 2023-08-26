package controller;


import java.io.IOException;
import java.util.Date;
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
			int id = 1;
			User user = (User) session.getAttribute("user");
			String date = new Date().toString();
			Cart cart = (Cart) session.getAttribute("cart");
			List<Item> items = cart.getItems();
			
			int total = 0;
			for (Item item: items) {
				total += item.getPrice();
			}
			
			Order order = new Order(id, user, date, total, items);		
			//RISKY CODE, NEED TO FIND A MORE ERROR PROOF METHOD POSIBLY
			order.setId(order.toString().hashCode());
			
			// Save the new order to the database
			OrderDAO dao = new OrderDAOImpl();
			dao.createOrder(order);
			
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
