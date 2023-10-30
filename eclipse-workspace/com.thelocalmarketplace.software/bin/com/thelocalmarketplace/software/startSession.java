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

public class StartSession {
	
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
	
	public void startSession() {
		shoppingCart.clear();
		this.totalWeight=0;
		this .Totalcost=0;
		this.inSession = true;
	}
	
	public void endSession() {
		setInSession(false);
	}
	
	
}
