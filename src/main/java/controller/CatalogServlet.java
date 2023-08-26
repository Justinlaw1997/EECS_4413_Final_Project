package controller;


import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ItemDAOImpl;
import model.Brand;
import model.Category;
import model.Item;

/**
 * Servlet implementation class CatalogServlet
 */
@WebServlet("/CatalogServlet")
public class CatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	// get author selections from index.html, and search for the books, get a arraylist of books
		
		ItemDAOImpl dao = new ItemDAOImpl();
		
		// The returned list of items
		List<Item> allItemsList;
		
		String sort = request.getParameter("sort");

		// Determines what items to return to the allItemsList by the chosen sort button
		if (sort == null) {
			allItemsList = dao.findAllItems();
		} else if (sort.equals("Prices: Ascending")) {
			allItemsList = dao.SortItemsByAscendingPrice();

		} else if (sort.equals("Prices: Descending")) {
			allItemsList = dao.SortItemsByDescendingPrice();

		} else if (sort.equals("Names: Alphabetically")) {
			allItemsList = dao.SortItemsByAlphabeticalOrder();

		} else if (sort.equals("Submit: Category")) {
			String chosenCategory = request.getParameter("categories");
			allItemsList = dao.findItemsByCategory(chosenCategory);
			
		} else if (sort.equals("Submit: Brand")) {
			String chosenBrand = request.getParameter("brands");
			allItemsList = dao.findItemsByBrand(chosenBrand);
			
		} else {
			allItemsList = dao.findAllItems();
		}
		
		// Grab categories
		List<Category> categories = dao.findAllCategories();
		
		// Grab brands
		List<Brand> brands = dao.findAllBrands();
		
		request.setAttribute("allItemsList", allItemsList);
		request.setAttribute("categories", categories);
		request.setAttribute("brands", brands);

    	

    	RequestDispatcher rd = request.getRequestDispatcher("jsp/CatalogView.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
