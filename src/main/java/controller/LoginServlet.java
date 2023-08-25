package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Address;
import model.User;
import dao.*; 

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * Handles all login, signup, guest, and admin page requests
	 * For Login, will take login if user-action is "login"
	 * For Admins, will take regular "login" user-action, but allows direciton to Admin page instead if "adminPage" is checked
	 * For sign up user-action, will send client to signup page, which will send it back with a "signupReg" user-action
	 * For Guest, "guest" user-action
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userAction = request.getParameter("user-action");
		//checkbox for admin to indicate they need to go to admin page
		String admin = request.getParameter("admin");
		if(userAction.equals("login")) {	
			//send to regular login
			
			//try to sign in first using dao
			String email = request.getParameter("email");
			String pass = request.getParameter("password");
			//checkbox to indicate admin wants to go to admin page
			String adminPage = request.getParameter("adminPage");
			UserDAO dao = new UserDAOImpl();
			
			//split into sign in result and customer id
			String [] result = dao.signIn(email, pass).split(" ", 2);
			if(result[0].equals("user does not exist")) {
				//set a user does not exist error alert to pop up on login page	
				request.setAttribute(" n", "error");
				
				//send back to login
				RequestDispatcher rd = request.getRequestDispatcher("/welcome.jsp");
				rd.forward(request, response);
				
			}else if(result[0].equals("incorrect password")) {
				//set an incorrect password alert to show up on login page	
				request.setAttribute("incrpass", "incrpass");
				
				//send back to login
				RequestDispatcher rd = request.getRequestDispatcher("/welcome.jsp");
				rd.forward(request, response);
				
			}else if(result[0].equals("admin") && adminPage.equals("go")) {
				//admin has been verified as an admin and wants to go to admin page
				
				User user = dao.findUserById(Integer.parseInt(result[1]));
				//store the user in the session
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
		        
		        //SEND ADMIN TO ADMINISTRATION PAGE HERE
		        
			}else if(result[0].equals("customer") || result[0].equals("admin")) {
				//user catalog regular use case
				
				User user = dao.findUserById(Integer.parseInt(result[1]));
				
				//store the user in the session (USER IS LOGGED IN)
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
		        
		        //send user to catalog
		        RequestDispatcher rd = request.getRequestDispatcher("/CatalogView.jsp");
				rd.forward(request, response);
			}
			
		
		
		}else if(userAction.equals("signup")) {
			 RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
				rd.forward(request, response);
			
		}else if(userAction.equals("guest")) {
			//makes sure any previous session account isn't logged in
	        HttpSession session=request.getSession();
	        session.setAttribute("user", null);
	        
	        //Send guest to catalog
	        RequestDispatcher rd = request.getRequestDispatcher("/CatalogView.jsp");
			rd.forward(request, response);
			
		}else if(userAction.equals("signupReg")) {
			
			//COLLECT USER INFO FROM SIGNUP.JSP
			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String streetAddress = request.getParameter("street-address");
			String province = request.getParameter("province");
			String country = request.getParameter("country");
			String postalCode = request.getParameter("postal-code");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Address address = new Address(1, streetAddress, province, country, postalCode, phone);
			//isadmin is 0 for all regular users, isadmin = 1 must be injected into server to make an admin
			User user = new User(1, firstName, lastName, address, 0, email, password);
			UserDAO dao = new UserDAOImpl();
			
			//REGISTER USER
			dao.registerUser(user);
			
			//store the user in the session (USER IS LOGGED IN)
	        HttpSession session=request.getSession();  
	        session.setAttribute("user", user);
	        
	        //send user to catalog
	        RequestDispatcher rd = request.getRequestDispatcher("/CatalogView.jsp");
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
