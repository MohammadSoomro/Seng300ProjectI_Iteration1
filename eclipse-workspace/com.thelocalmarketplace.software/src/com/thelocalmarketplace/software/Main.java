package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class Main implements ElectronicScaleListener {
	
	private SelfCheckoutStation station = new SelfCheckoutStation();
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
		//if (!station.baggingArea.isDisabled()) {
		if (!scale.isDisabled() & inSession) {
			try {
				//if (mass.difference(station.baggingArea.getCurrentMassOnTheScale()).abs().compareTo(station.baggingArea.getSensitivityLimit()) >= 0) {
				if (mass.difference(((ElectronicScale)scale).getCurrentMassOnTheScale()).abs().compareTo(scale.getSensitivityLimit()) >= 0) {
					station.scanner.disable();
					station.coinSlot.disable();
				}
			} catch (OverloadedDevice e) {}
		}		
	}
}
