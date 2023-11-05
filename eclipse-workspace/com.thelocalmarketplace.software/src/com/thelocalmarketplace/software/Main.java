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
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;



public class Main implements ElectronicScaleListener {
	
	protected static SelfCheckoutStation station;
	
	protected static ArrayList<BarcodedProduct> shoppingCart;
	protected static boolean inSession = false;
	private long totalCost;
	boolean skipBagging = false;

	/**
	 * This method sets the flag to true to start a session
	 * @param Takes no parameter
	 */
	@Override
	public boolean getInSession() {
		return inSession; 
	}

	/**
	 * Returns weather a session is in progress.
	 * 
	 * @param inSession boolean: which will indidcate weather a session is in progress
	 */
	@Override
	public void setInSession(boolean inSession) {	
		this.inSession = inSession;
	}

	/**
	 * This method is called to calculate the total pcost of all products in the shopping cart 
	 * @param shoppingCart an arrayList of items in the shopping cart 
	 */
	@Override
	public long totalPrice(ArrayList<BarcodedProduct> shoppingCart) {
		long sumOfItemPrices = 0;
		for(int i = 0; i < shoppingCart.size(); i++) {
			sumOfItemPrices += shoppingCart.get(i).getPrice();
		}
		this.totalCost = sumOfItemPrices;
		return sumOfItemPrices;
	}

	/**
	 * This method starts the session
	 * @param no parameter
	 */
	@Override
	public void startSession() throws Exception {
		if(getInSession()) {
			throw new Exception();
		}
		
		shoppingCart.clear();
		this.totalWeight = 0;
		this.totalCost = 0;
		this.inSession = true;
	} 


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

	/**
	 * This method is called when station is ready to pay for order
	 * @param coin: a coin object to pay for the order 
	 */
	@Override
	public void payForOrder(Coin coin) throws ClassNotFoundException, DisabledException, CashOverloadException {		
		long paidAmount =0;
		while (paidAmount != this.totalCost || paidAmount > this.totalCost){
			station.coinSlot.receive(coin);
			paidAmount = coin.getValue().longValue();
		}
		endSession();
	}

	/**
	 * This method is called when a session is completed
	 * @param no parameter
	 */
	@Override
	public void endSession() {
		setInSession(false);
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
