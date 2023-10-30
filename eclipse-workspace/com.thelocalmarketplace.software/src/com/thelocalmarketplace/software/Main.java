package com.thelocalmarketplace.software;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;


public class Main {


	/**
	 * Details about the item to add must be provided to the system  CHECK
	 * System : Blocks the self-checkout from further interaction    
	 * System : The expected weight in the bagging area is updated
	 * System : Signals to the customer to place item in bagging area
	 */
	
	/**
	 * @param a barcode given by listeners of barcodeScanner
	 * @return 
	 */
	public static void addItemViaScan(Barcode barcode) {
		// get BarcodedProduct mass from database
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		
		// update expected weight in bagging area
		Mass productMass = new Mass(product.getExpectedWeight());
		Mass currentWeightInBaggingArea = null;// ElectronicScale.getCurrentMassOnTheScale();
		Mass expectedWeightInBaggingArea = productMass.sum(currentWeightInBaggingArea);
		
		// blocks system from further adds until product is placed in bagging area
		/**
		 * while currentWeightInBaggingArea != expectedWeightInBaggingArea {
		 * 
		 * 	display message "please place item in bagging area"
		 * 
		 * 	display:
		 * 	skipBaggingButton(){
		 * 		expectedWeightInBaggingArea = currentWeightInBaggingArea;
		 * 	}
		 * 
		 * }
		 */
	}

}
