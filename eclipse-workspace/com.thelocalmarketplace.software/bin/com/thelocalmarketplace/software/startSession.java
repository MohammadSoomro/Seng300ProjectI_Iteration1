/*
 * This Package contains the source files to run the start Session program
 * 
 * Created by: 
 * Name: Ajaypal Sallh
 * Name: 30023811
 */
package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class startSession {
	
	private boolean inSession = false;
	private BigDecimal totalcost = 0;
	private int totalWeight = 0;
	private ArrayList<BarcodedProduct> shoppingCart = new ArrayList<>();
	
	public boolean getInSession() {
		return inSession;
	}
	
	public void endSession() {
		setInSession(false);
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
	
	public void startSession() throws Exception{
		if(getInSession()) {
			throw new Exception();
		}
		shoppingCart.clear();
		this.totalWeight=0;
		this.totalcost= new BigDecimal(0);
		this.inSession = true;		
	}
	
	public void payForOrder(Coin coin) throws ClassNotFoundException, DisabledException, CashOverloadException {
		float totalPrice = totalPrice(shoppingCart);		
		station.coinSlot.attach(Observer);
		
		// Whatever chris got
		while(this.totalCost.compareTo(coin.getValue()) > 0) {
		station.coinSlot.receive(coin);
		this.totalCost.subtract(coin.getValue());
		}
	}
		
	
}
