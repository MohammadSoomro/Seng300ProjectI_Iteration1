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
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;


public class Main extends BarcodeScanner implements BarcodeScannerListener{
	
	private static ArrayList<BarcodedProduct> shoppingCart = new ArrayList();
	private static SelfCheckoutStation station = new SelfCheckoutStation();
	private static boolean inSession;
	
	/**
	 * This method represents when the customer scans an item.
	 * The item is added to the items list.
	 * 
	 * @param item The item that was scanned
	 */
	@Override
	public synchronized void scan(BarcodedItem item) {
		super.scan(item);
		boolean skipBagging;
		if(skipBagging) {
			station.baggingArea.removeAnItem(item);
			notifyAttendantStation();
		}
		station.baggingArea.addAnItem(item);
	}
	
	/**
	 * This method is called when an item has been scanned.
	 * Adds the product to the shopping cart.
	 * 
	 * @param barcodeScanner The barcode scanner that cause the event
	 * @param barcode The barcode of the scanned item
	 */
	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		if (!barcodeScanner.isDisabled() & inSession) {
			station.baggingArea.addAnItem(null);
		}
		BarcodedProduct product = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		shoppingCart.add(product);
	}
	
	private static void notifyAttendantStation() {}
	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}
	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {}
	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {}
	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {}
	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {}
	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {}

}
