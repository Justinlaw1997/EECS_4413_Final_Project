package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.User;

import java.io.IOException;
import java.util.List;

import dao.OrderDAO;
import dao.OrderDAOImpl;

/**
 * Servlet implementation class CustomerOrderServlet
 */
public class CustomerOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerOrderServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		User user = (User) request.getSession().getAttribute("user");		
		OrderDAO orderDao = new OrderDAOImpl();
		
		// Check to see if an Order deletion has been requested
		if (request.getParameter("delete") != null) {
			orderDao.deleteOrder(Integer.parseInt(request.getParameter("delete")));
		}
	 
		List<Order> orders;	
		orders = orderDao.findAllOrdersByCustomer(user.getId());
		
		request.setAttribute("orders", orders);
		RequestDispatcher rd;
    	rd = request.getRequestDispatcher("jsp/CustomerOrderView.jsp");
    	rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
