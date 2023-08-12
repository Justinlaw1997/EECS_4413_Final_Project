package dao;

import java.util.List;

import model.Order;

public interface OrderDAO {
	
	public List<Order> findAllOrders();
	
	public List<Order> findAllOrdersByCustomer(int customerId);
	
	public List<Order> findAllOrdersByItem(String itemId);
	
	public List<Order> findAllOrdersByBrand(String brand);
	
	public List<Order> findAllOrdersByCategory(String category);
	
	public List<Order> findAllOrdersByDate(String date);
	
	public void createOrder(Order order);
	
	public void deleteOrder(Order order);
	
}
