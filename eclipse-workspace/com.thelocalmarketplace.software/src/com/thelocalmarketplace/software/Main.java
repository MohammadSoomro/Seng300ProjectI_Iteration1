package com.thelocalmarketplace.software;

import java.util.ArrayList;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.hardware.BarcodedProduct;


public class Main {
	
	
	private ArrayList<BarcodedProduct> shoppingCart = new ArrayList<>();
	
	/**
	 * Obtains the expected mass in the bagging area.
	 * 
	 * @return The expected mass.
	 */
	public Mass getExpectedMass() {
		Mass expectedMass = new Mass(0);
		
		for (BarcodedProduct items : shoppingCart) {
			 expectedMass.sum(new Mass(items.getExpectedWeight() * Mass.MICROGRAMS_PER_GRAM));
		}
		
		return expectedMass;
	}
	
	

}
