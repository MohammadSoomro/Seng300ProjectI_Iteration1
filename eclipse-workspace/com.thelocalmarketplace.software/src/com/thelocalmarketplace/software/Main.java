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
	
	private SelfCheckoutStation station = new SelfCheckoutStation();
	
	private ArrayList<BarcodedProduct> shoppingCart = new ArrayList<BarcodedProduct>();
	private boolean inSession = false;
	
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
			
			double tempWeight = 0;
			
			for (BarcodedProduct items : shoppingCart) 
				tempWeight += items.getExpectedWeight();
			
			Mass expectedMass = new Mass(tempWeight * Mass.MICROGRAMS_PER_GRAM);
			
			if (mass.difference(expectedMass).abs().compareTo(scale.getSensitivityLimit()) >= 0) {
				station.scanner.disable();
				station.coinSlot.disable();
			}
		}		
	}
}
