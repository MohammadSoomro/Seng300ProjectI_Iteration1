/*
 * This Package contains the source files to run the start Session program
 * 
 * Created by: 
 * Name: Ajaypal Sallh
 * Name: 30023811
 */
package com.thelocalmarketplace.software;

import java.util.ArrayList;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class startSession {
	
	private boolean inSession = false;
	private int Totalcost = 0;
	private int totalWeight = 0;
	private ArrayList<BarcodedProduct> shoppingCart = new ArrayList<>();
	
	public boolean getInSession() {
		return inSession;
	}

	public void setInSession(boolean inSession) {
		this.inSession = inSession;
	}
	
	//Calculates total price of items in a user's shopping cart
	public float totalPrice(ArrayList<BarcodedProduct> itemsFromCart) {
		int i= 0;
		float sumOfItemPrices = 0;
		while (i <= itemsFromCart.size()) {
			float price = itemsFromCart[i].price();
			sumOfItemPrices += price;
			i++;
		}
		
		return sumOfItemPrices;
	}
	
	public void startSession() {
		shoppingCart.clear();
		this.totalWeight=0;
		this .Totalcost=0;
		this.inSession = true;
		
		
		//Logic code for what happens when customer chooses to pay via coin
		
		float totalPrice = totalPrice(shoppingCart);
		
		//initialize listeners in a loop
		
		//should expect invalidCoinException, NullCoinException, others?
		while (totalPrice > 0) {
			
			// listener.listens
			//newMoney = listener.returned
			//totalPrice -= newMoney
		}
		
	}
	
	public void endSession() {
		setInSession(false);
	}
	
	
}
