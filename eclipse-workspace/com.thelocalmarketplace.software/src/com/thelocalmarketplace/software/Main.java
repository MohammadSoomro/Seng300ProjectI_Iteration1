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
