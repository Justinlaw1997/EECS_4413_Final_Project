package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			String resultString = dao.signIn(email, pass);
			String [] result = dao.signIn(email, pass).split(" ", 2);

			
			System.out.println("Got result from LOGIN with email = " + email + ": ");
			for(String s: result) {
				System.out.println(s);
			}
			if(resultString.equals("user does not exist")) {
				//set a user does not exist error alert to pop up on login page	
				request.setAttribute("no-user", "error");
				
				//send back to login
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/welcome.jsp");
				rd.forward(request, response);
				
			}else if(resultString.equals("incorrect password")) {
				//set an incorrect password alert to show up on login page	
				request.setAttribute("incrpass", "incrpass");
				
				//send back to login
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/welcome.jsp");
				rd.forward(request, response);
				
			}else if(result[0].equals("admin") && adminPage != null && adminPage.equals("go")) {
				//admin has been verified as an admin and wants to go to admin page
				
				User user = dao.findUserById(Integer.parseInt(result[1]));
				//store the user in the session
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
		        
		        //SEND ADMIN TO ADMINISTRATION PAGE HERE
		        RequestDispatcher rd = request.getRequestDispatcher("/AdminServlet");
				rd.forward(request, response);
		        
			}else if(result[0].equals("customer") || result[0].equals("admin")) {
				//user catalog regular use case
				User user = dao.findUserById(Integer.parseInt(result[1]));
				
				//store the user in the session (USER IS LOGGED IN)
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
		        
		        //send user to catalog
		        RequestDispatcher rd = request.getRequestDispatcher("/CatalogServlet");
				rd.forward(request, response);
			}
			
		
		
		}else if(userAction.equals("signup")) {
			 RequestDispatcher rd = request.getRequestDispatcher("/jsp/signup.jsp");
				rd.forward(request, response);
			
		}else if(userAction.equals("guest")) {
			//makes sure any previous session account isn't logged in
	        HttpSession session=request.getSession();
	        session.setAttribute("user", null);
	        
	        //Send guest to catalog
	        RequestDispatcher rd = request.getRequestDispatcher("/CatalogServlet");
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
			
			String emailReg = ".+@.+\\.+";
			//Digit must occur once
			String passReg1 = "(?=.*[0-9])";
			//Special Character for pass
			String passReg2 = "(?=.*[@#$%^&-+=()])";
			//Uppercase atleastOnce
			String passRegCaps = "(?=.*[A-Z])";

			
			System.out.println("Starting validation");
			//Reg1
	        Pattern p = Pattern.compile(passReg1);
			Matcher m = p.matcher(password);
			//Reg1
	        Pattern p2 = Pattern.compile(passReg2);
			Matcher m2 = p2.matcher(password);
			//RegCaps
	        Pattern p3 = Pattern.compile(passRegCaps);
			Matcher m3 = p3.matcher(password);
			//RegWS

			//Email
			Pattern p5 = Pattern.compile(emailReg);
			Matcher m5 = p5.matcher(email);
		
			
			System.out.println("TRYIUNG TO VALIDATE: " + password + " against: " + m2.lookingAt() + " m1: " + m.lookingAt());
			if(!m.lookingAt()) {
				//incorrect!!
				System.out.println("Digit missing");
				request.setAttribute("input-error", "Password" + password + "must include a digit atleast once, " + passReg1);
				 RequestDispatcher rd = request.getRequestDispatcher("/jsp/signup.jsp");
					rd.forward(request, response);
			}else if(!m2.lookingAt()) {
				//incorrect!!
				request.setAttribute("input-error", "Password must include a special character atleast once");
				 RequestDispatcher rd = request.getRequestDispatcher("/jsp/signup.jsp");
					rd.forward(request, response);
			}else if(!m3.lookingAt()) {
				//incorrect!!
				request.setAttribute("input-error", "Password must include a capital character atleast once");
				 RequestDispatcher rd = request.getRequestDispatcher("/jsp/signup.jsp");
					rd.forward(request, response);
			}else if(!m5.lookingAt()){
				//incorrect!!
				request.setAttribute("input-error", "Email is incorrect format");
				 RequestDispatcher rd = request.getRequestDispatcher("/jsp/signup.jsp");
					rd.forward(request, response);
			}else {
				Address address = new Address();
				address.setStreetAddress(streetAddress);
				address.setProvince(province);
				address.setCountry(country);
				address.setProvince(province);
				address.setPostalCode(postalCode);
				address.setPhone(phone);
				
				//isadmin is 0 for all regular users, isadmin = 1 must be changed from the admin page
				User user = new User();
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setAddress(address);
				user.setIsAdmin(0);
				user.setEmail(email);
				user.setPassword(password);			
	      
				UserDAO dao = new UserDAOImpl();
				
				//REGISTER USER
				int id = dao.registerUser(user);
				user.setId(id);
				
				//store the user in the session (USER IS LOGGED IN)
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
		        
		        //send user to catalog
		        RequestDispatcher rd = request.getRequestDispatcher("/CatalogServlet");
				rd.forward(request, response);
			}
		

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
