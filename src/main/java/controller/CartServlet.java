package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.*;
import model.*;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	 
		// Attempts to acquire the session, creates one if it doesnt exist
		HttpSession session = request.getSession(true);
		
		// Attempts to acquire the cart, creates one if it doesnt exist
		Cart cart;
		synchronized (session) {
			cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
				session.setAttribute("cart", cart);
			}
		}
	 
	    RequestDispatcher rd;
	      
	    try {
	 
	    	String todo = request.getParameter("todo");
	         
	    	if (todo.equals("add") ) {
	    		
	    		// Items coming from the Catalog Page
			    String[] ids = request.getParameterValues("id");
			    
			    // Item coming from a single Item Page
			    String singleId = request.getParameter("singleId");
          
			    if (ids == null) {
			    	if (singleId == null) {
		    			rd = request.getRequestDispatcher("jsp/CartNoItemsView.jsp");
		    			rd.forward(request, response);
			    	}
			    	
			    	// Set the single item to an array and continue
			    	ids = new String[1];
			    	ids[0] = singleId;
			     }
	    		
	    		for (String id : ids) {
	
	    			// Finds the specific item within the database
	    			ItemDAO dao = new ItemDAOImpl();
	    			Item it  = dao.findItemById(id);
	    			it.setQuantity(Integer.parseInt(request.getParameter("qty" + id)));
	    			
	    			// Sets all of the item description names to a variable
	    			String itemID = it.getItemID();
	    			String name = it.getName();
	    			String description = it.getDescription();
	    			Category category = it.getCategory();
	    			Brand brand = it.getBrand();
	    			int price = it.getPrice();
	    			int quantity = it.getQuantity();
	    			String image = it.getImage();
  
	    			// Adds the specific item to the cart of the current session user
	    			cart.add(itemID, name, description, category, brand, price, quantity, image);
           
	    		}
 
	    		// Forwards the request to the view jsp
	    		rd = request.getRequestDispatcher("jsp/CartView.jsp");
	    		rd.forward(request, response);
  
	    	} 
	    	else if (todo.equals("update")) {
	    		// Updates the quantity of the item
	    		String id = request.getParameter("id"); 
	    		int qtyNew = Integer.parseInt(request.getParameter("qty" + id));
	    		cart.update(id, qtyNew);

	    		rd = request.getRequestDispatcher("jsp/CartView.jsp");
	    		rd.forward(request, response);
	    	}
	    	else if (todo.equals("remove")) { 
	    		// Removes the item from the cart
	    		String id = request.getParameter("id");  
	    		cart.remove(id);

	    		rd = request.getRequestDispatcher("jsp/CartView.jsp");
	    		rd.forward(request, response);
	    	} 
	    }finally {
     
    
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
