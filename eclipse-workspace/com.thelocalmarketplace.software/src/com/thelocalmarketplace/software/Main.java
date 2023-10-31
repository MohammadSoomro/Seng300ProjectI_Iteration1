package com.thelocalmarketplace.software;

import java.util.ArrayList;
import java.util.Scanner;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;


public class Main {
	
	private static ArrayList<BarcodedProduct> shoppingCart = new ArrayList();
	private static SelfCheckoutStation station = new SelfCheckoutStation();

	/**
	 * Details about the item to add must be provided to the system  CHECK
	 * System : Blocks the self-checkout from further interaction    
	 * System : The expected weight in the bagging area is updated
	 * System : Signals to the customer to place item in bagging area
	 */
	
	/**
	 * @param a barcode given by listeners of barcodeScanner
	 * @return 
	 * @throws OverloadedDevice 
	 */
	public static void addItemViaScan(Barcode barcode) throws OverloadedDevice {
		boolean skipBagging = false;
		Scanner myScanner = new Scanner(System.in);
		// get BarcodedProduct mass from database
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		
		// update expected weight in bagging area
		Mass productMass = new Mass(product.getExpectedWeight());
		Mass currentWeightInBaggingArea = station.baggingArea.getCurrentMassOnTheScale();
		Mass expectedWeightInBaggingArea = currentWeightInBaggingArea.sum(productMass);
		
		// blocks system from further adds until product is placed in bagging area or bagging is skipped
		while (currentWeightInBaggingArea != expectedWeightInBaggingArea) {
			System.out.print("please place item in bagging area");
			System.out.print("Skip bagging?(enter 1 for yes): ");
			String response = myScanner.nextLine();
			if (response == "1") {
				skipBagging = true;
				expectedWeightInBaggingArea = currentWeightInBaggingArea;
			}
		}
		shoppingCart.add(product);
		
		
	}

}
