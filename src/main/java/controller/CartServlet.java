package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
		response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	 
	      // Retrieve current HTTPSession object. If none, create one.
	      HttpSession session = request.getSession(true);
	      Cart cart;
	      synchronized (session) {  // synchronized to prevent concurrent updates
	         // Retrieve the shopping cart for this session, if any. Otherwise, create one.
	         cart = (Cart) session.getAttribute("cart");
	         if (cart == null) {  // No cart, create one.
	            cart = new Cart();
	            session.setAttribute("cart", cart);  // Save it into session
	         }
	      }
	 
	      
	      try {
	         out.println("<html><head><title>Shopping Cart</title><link href=\"css/style.css\" rel=\"stylesheet\"></head><body>");
	         out.println("<h2>Your Shopping Cart</h2>");
	         
	 
	         String todo = request.getParameter("todo");
	        	         
	         if (todo.equals("add") ) {

	 		// Items coming from the Catalog Page
		    String[] ids = request.getParameterValues("id");
		    // Item coming from a single Item Page
		    String singleId = request.getParameter("singleId");
		              
		    if (ids == null) {
		    	if (singleId == null) {
		    		out.println("<h3>Please Select a Book!</h3></body></html>");
				    return;
		    	}
		    	// set the single item to an array and continue
		    	ids = new String[1];
		    	ids[0] = singleId;
		     }
		     for (String id : ids) {
		    	 ItemDAO dao = new ItemDAOImpl();
		    	 Item it  = dao.findItemById(id);
		    	 it.setQuantity(Integer.parseInt(request.getParameter("qty" + id)));

		    	 
		    	 String itemID = it.getItemID();
		    	 String name = it.getName();
		    	 String description = it.getDescription();
		    	 Category category = it.getCategory();
		    	 Brand brand = it.getBrand();
		    	 int price = it.getPrice();
		    	 int quantity = it.getQuantity();
		    	 String image = it.getImage();
			  
		        cart.add(itemID, name, description, category, brand, price, quantity, image);
		               
		      }
	      
	         } 
	         else if (todo.equals("update")) {
	            String id = request.getParameter("id"); 
	            int qtyNew = Integer.parseInt(request.getParameter("qty" + id));
	            System.out.println("cart update new");
	            System.out.println(id);

	            System.out.println(qtyNew);
	            cart.update(id, qtyNew);
//	            
//	            List<Item> items = cart.getItems();
//	            
//	            for (Item item : items) {
//		            System.out.println(item.getName());
//		            System.out.println(item.getQuantity());
//	            }
	         }
	         else if (todo.equals("remove")) { 
	            String id = request.getParameter("id");  
	            System.out.println("cart remove");
	            cart.remove(id);
	         }
	 
	         // All cases - Always display the shopping cart
	         if (cart.isEmpty()) {
	            out.println("<p>Your shopping cart is empty</p>");
	         } else {
	            out.println("<table border='1' cellpadding='6'>");
	            out.println("<tr>");
	            out.println("<th>Image</th>");
	            out.println("<th>Name</th>");
	            out.println("<th>Category</th>");
	            out.println("<th>Brand</th>");
	            out.println("<th>Price</th>");
	            out.println("<th>Update</th>");
	            out.println("<th>Remove</th></tr>");

	 
	            float totalPrice = 0f;
	            for (Item it : cart.getItems()) {
	               String id = it.getItemID();
	               String name = it.getName();
		    	   Category category = it.getCategory();
		    	   Brand brand = it.getBrand();
		    	   int price = it.getPrice();
		    	   int quantity = it.getQuantity();
		    	   String image = it.getImage();
	 
	               out.println("<tr>");
	           	  out.println("<td><image src=" + image + "></td>");
	          	  out.println("<td>" + name + "</td>");
	          	  out.println("<td>" + category.getName() + "</td>");
	          	  out.println("<td>" + brand.getName() + "</td>");
	          	  out.println("<td>" + price + "</td>");

	               out.println("<td><form method='get'>");
	               out.println("<input type='hidden' size='3' name='todo' value='update' />"); //should be hidden
	               out.println("<input type='hidden' size='3' name='id' value='" + id + "' />"); // should be hidden
	               out.println("<input type='text' size='3' name='qty" + id + "' value='" + quantity + "' />" );
	               out.println("<input type='submit' value='Update' />");
	               out.println("</form></td>");
	 
	               // a small form to do remove
	               out.println("<td><form method='get'>");
	               out.println("<input type='hidden' size='3' name='todo' value='remove' >"); //should be hidden
	               out.println("<input type='hidden' size='3' name='id' value='" + id + "'>"); // should be hidden
	               out.println("<input type='submit' value='Remove'>");
	               out.println("</form></td>");
	               out.println("</tr>");
	               totalPrice += price * quantity;
	            }
	            out.println("<tr><td colspan='6' align=''>Total Price: $");
	            out.printf("%.2f</td></tr>", totalPrice);
	            out.println("</table>");
				session.setAttribute("totalPrice", totalPrice);
	         }
	         
	         out.println("<p><a href='/EECS4413FinalProjectJLI//CatalogServlet'>Continue shopping...</a></p>");
	 
	         // Display the Checkout
	         if (!cart.isEmpty()) {
	            out.println("<br />");
	            out.println("<form method='get' action='OrderServlet'>");
	            out.println("<input type='submit' value='CHECK OUT'>");                
	                  
	            
	            out.println("</form>");
	         }
	 
	         out.println("</body></html>");
	 
	      }finally {
	         out.close();
	         
	        
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
