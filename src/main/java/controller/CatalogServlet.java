package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CatalogDAOImpl;
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
		
		CatalogDAOImpl itemList = new CatalogDAOImpl();
		
		// TESTING DAO IMPLEMENTATION (TO BE REMOVED)
		List<Item> items1 = itemList.findAllItems();
		System.out.println("-------------------");
		for(Item item: items1) {
			System.out.println(item.toString());
		}
		List<Item> items2 = itemList.SortItemsByAscendingPrice();
		System.out.println("-------------------");
		for(Item item: items2) {
			System.out.println(item.getPrice());
		}
		List<Item> items3 = itemList.SortItemsByDescendingPrice();
		System.out.println("-------------------");
		for(Item item: items3) {
			System.out.println(item.getPrice());
		}
		List<Item> items4 = itemList.SortItemsByAlphabeticalOrder();
		System.out.println("-------------------");
		for(Item item: items4) {
			System.out.println(item.getName());
		}
		List<Item> items5 = itemList.findItemsByCategory("video game");
		System.out.println("-------------------");
		for(Item item: items5) {
			System.out.println(item.getName());
		}
		List<Item> items6 = itemList.findItemsByBrand("Dell");
		System.out.println("-------------------");
		for(Item item: items6) {
			System.out.println(item.getName());
		}
		List<Category> categories = itemList.findAllCategories();
		System.out.println("-------------------");
		for(Category category: categories) {
			System.out.println(category.toString());
		}
		List<Brand> brands = itemList.findAllBrands();
		System.out.println("-------------------");
		for(Brand brand: brands) {
			System.out.println(brand.toString());
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
