package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userAction = request.getParameter("user-action");
		String admin = request.getParameter("admin");
		if(userAction.equals("login")) {	
			//send to regular login
			
			//try to sign in first using dao
			String email = request.getParameter("email");
			String pass = request.getParameter("password");
			
			UserDAO dao = new UserDAOImpl();
			
			//split into sign in result and customer id
			String [] result = dao.signIn(email, pass).split(" ", 2);
			if(result[0].equals("user does not exist")) {
				request.setAttribute("error", "error");
			}else if(result[0].equals("incorrect password")) {
				
			}else if(result[0].equals("admin")) {
				
				User user = dao.findUserById(Integer.parseInt(result[1]));
				
				//store the user in the session
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
		        if(admin.isEmpty()) {
		        	//send to regular catalog view
		        	
		        }else {
		        	//send to admin view
		        	
		        }
		        //check if 
			}else if(result[0].equals("customer")) {
				
				User user = dao.findUserById(Integer.parseInt(result[1]));
				
				//store the user in the session
		        HttpSession session=request.getSession();  
		        session.setAttribute("user", user);
			}else {
				//unexpected
				
			}
			
		
		
		}else if(userAction.equals("signup")) {
			
		}else if(userAction.equals("guest")) {
			
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
