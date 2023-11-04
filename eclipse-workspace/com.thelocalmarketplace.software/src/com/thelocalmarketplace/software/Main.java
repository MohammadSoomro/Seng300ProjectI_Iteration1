package com.thelocalmarketplace.software;

import java.util.ArrayList;
import java.util.Scanner;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;


public class Main implements ElectronicScaleListener, BarcodeScannerListener{
	
	private static ArrayList<BarcodedProduct> shoppingCart = new ArrayList();
	private static SelfCheckoutStation station = new SelfCheckoutStation();
	private boolean inSession = true;
	private static Mass zeroMass = new Mass(0);
	private static Mass tempMass = zeroMass;

	/**
	 * Details about the item to add must be provided to the system  CHECK
	 * System : Blocks the self-checkout from further interaction    
	 * System : The expected weight in the bagging area is updated
	 * System : Signals to the customer to place item in bagging area
	 */
	
	/**
	 * @return 
	 * 
	 */
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		if (!scale.isDisabled() & inSession) {
			
			double tempWeight = 0;
			
			for (BarcodedProduct products : shoppingCart) 
				tempWeight += products.getExpectedWeight();
			
			Mass expectedMass = new Mass(tempWeight * Mass.MICROGRAMS_PER_GRAM);
			
			if (mass.difference(expectedMass).abs().compareTo(scale.getSensitivityLimit()) >= 0) {
				station.scanner.disable();
				station.coinSlot.disable();
			}
			tempMass = mass;
		}		
	}
	
	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		addItemViaScan(barcode);
	}
	/**
	 * @param a barcode given by listeners of barcodeScanner
	 * @return 
	 * @throws OverloadedDevice 
	 */
	public static void addItemViaScan(Barcode barcode) {

		// get BarcodedProduct mass from database
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		double productMassInMicroGrams = product.getExpectedWeight() * Mass.MICROGRAMS_PER_GRAM;
		Mass productMassInGrams = new Mass(product.getExpectedWeight());
		// construct an Item using BarcodedProduct info
		Item item = new BarcodedItem(product.getBarcode(), productMassInGrams);
		// update expected weight in bagging area
		
		// add item to cart and Items list in scale
		shoppingCart.add(product);
		station.baggingArea.addAnItem(item);
		boolean skipBagging;
		/**
		 *  blocks system from further adds until
		 *  product is placed in bagging area or bagging is skipped (weight discrepancy is solved)
		 */
		if(skipBagging = true) {
			station.baggingArea.removeAnItem(item);
			notifyAttendantStation();
		}
	}

		

	private static void notifyAttendantStation() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		// TODO Auto-generated method stub
		
	}

}
