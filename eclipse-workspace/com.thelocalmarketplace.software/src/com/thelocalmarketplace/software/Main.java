/**
* @author Gurjit Samra : 30172814
* @author Mohammad Soomro : 30130440
* @author Christian Nielson: 30185718
* @author Ajaypal Sallh: 30023811
* @author Dongwen Tian: 30181813
*
*/




package com.thelocalmarketplace.software;

import java.util.ArrayList;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class Main implements ElectronicScaleListener {
	
	protected static SelfCheckoutStation station;
	
	protected static ArrayList<BarcodedProduct> shoppingCart;
	protected static boolean inSession;
	boolean skipBagging = false;
	/**
	 * This method represents when the customer scans an item.
	 * The item is added to the items list.
	 * 
	 * @param item The item that was scanned
	 */
	@Override
	public synchronized void scan(BarcodedItem item) {
		super.scan(item);
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
	
	
	/**
	 * This event is triggered when the mass on the scale changes.
	 * If there is a discrepancy between the expected mass and the current
	 * mass, the coin slot and Barcode scanner are disabled.
	 */
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		if (!scale.isDisabled() & inSession) {
			
			double tempWeight = 0.0;
			
			for (BarcodedProduct products : shoppingCart) 
				tempWeight += products.getExpectedWeight();
			
			Mass expectedMass = new Mass(tempWeight);
			
			if (mass.difference(expectedMass).abs().compareTo(station.baggingArea.getSensitivityLimit()) >= 0) {
				station.scanner.disable();
				station.coinSlot.disable();
			}
		}
	}
	
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
