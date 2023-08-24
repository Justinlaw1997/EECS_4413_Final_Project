package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
 
   private List<Item> cart; 
 
   // constructor
   public Cart() {
      cart = new ArrayList<Item>();
   }
 
   // Add a Item into this Cart, with qtyOrdered. If the item of id already exists, update the qty ordered
   // if not, create a new item.
   public void add(String itemID, String name, String description, Category category, Brand brand, int price, int quantity) {
 	  Boolean itemFound = false;
      for(Item item: cart) {
    	  if (item.getItemID().equals(itemID)) {
    		  item.setQuantity(item.getQuantity() + quantity);
    		  itemFound = true;
    		  break;
    	  }
      }
	  
	  if (itemFound == false) {
		  Item newItem = new Item(itemID, name, description, category, brand, price, quantity);
		  newItem.setQuantity(quantity);
		  cart.add(newItem);
	  }
   }
 
   // Update the quantity for the given id, of total orderqty
   public void update(String id, int newQty) {
	   for (Item item: cart) {
		   if (item.getItemID().equals(id)) {
			   item.setQuantity(newQty);
		   }
	   }
   }
 
   // Remove a Item given its id
   public void remove(String id) {
      Iterator<Item> iter = cart.iterator();
      while (iter.hasNext()) {
    	  Item b = iter.next();
         if (b.getItemID().equals(id)) {
        	 cart.remove(b);
        	 break;
         }
      }
   }
 
   // Get the number of Items in this Cart
   public int size() {
      return cart.size();
   }
 
   // Check if this Cart is empty
   public boolean isEmpty() {
      return size() == 0;
   }
 
   // Return all the Items in a List<Item>
   public List<Item> getItems() {
      return cart;
   }
 
   // Remove all the items in this Cart
   public void clear() {
      cart.clear();
   }
}