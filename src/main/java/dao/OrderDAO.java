package dao;

import java.util.List;

import model.Order;

public interface OrderDAO {
	
	public Order findOrderById(int id);
	
	public List<Order> findAllOrders();
	
	public List<Order> findAllOrdersByCustomer(int customerId);
	
	public List<Order> findAllOrdersByItem(String itemId);
	
	public List<Order> findAllOrdersByBrand(String brand);
	
	public List<Order> findAllOrdersByCategory(String category);
	
	public List<Order> findAllOrdersByDate(String date);
	
	public int createOrder(Order order);
	
	public void deleteOrder(int id);
	
}
