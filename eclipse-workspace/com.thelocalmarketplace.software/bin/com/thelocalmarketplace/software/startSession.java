/* Written by: 
 * Name: Ajaypal Sallh
 * UCID: 30023811
 and 
 * Name: Christian Nielsen
 * UCID: 30185718
 */

package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.ArrayList;
/*
 * ToDo:
 * 	- create instance, and attach listener 
 */

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinSlotObserver;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class startSession {
	
	class coinSlotListener implements CoinSlotObserver {

		@Override
		public void enabled(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void turnedOn(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void turnedOff(IComponent<? extends IComponentObserver> component) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void coinInserted(CoinSlot slot) {
			// TODO Auto-generated method stub
			
		}

	}
	private boolean inSession = false;
	private BigDecimal totalCost;
	private int totalWeight = 0;
	private ArrayList<BarcodedProduct> shoppingCart = new ArrayList<>();
	public SelfCheckoutStation station = new SelfCheckoutStation();
	private coinSlotListener Observer = new coinSlotListener();
	
	
	
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
		while (i <= shoppingCart.size()) {
			float price = shoppingCart.get(i).getPrice();
			sumOfItemPrices += price;
			i++;
		}
		
		return sumOfItemPrices;
	}
	
	public void startSession() throws Exception {
		if(getInSession()) {
			throw new Exception();
		}
		
		shoppingCart.clear();
		this.totalWeight = 0;
		this.totalCost = new BigDecimal(0);
		this.inSession = true;
	
		
	}
	
	public void payForOrder(Coin coin) throws ClassNotFoundException, DisabledException, CashOverloadException {
		float totalPrice = totalPrice(shoppingCart);		
		station.coinSlot.attach(Observer);
		
		while(this.totalCost.compareTo(coin.getValue()) > 0) {
		station.coinSlot.receive(coin);
		this.totalCost.subtract(coin.getValue());
		}
	}
		
	
	public void endSession() {
		setInSession(false);
	}
	
	
}