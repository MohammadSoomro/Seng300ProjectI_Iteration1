package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class Main implements ElectronicScaleListener {
	
	private SelfCheckoutStation station = new SelfCheckoutStation();
	
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
	
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		if (!station.baggingArea.isDisabled()) {
			try {
				if (mass.difference(station.baggingArea.getCurrentMassOnTheScale()).abs().compareTo(station.baggingArea.getSensitivityLimit()) >= 0) {
					station.scanner.disable();
					station.coinSlot.disable();
				}
			} catch (OverloadedDevice e) {}
		}		
	}
}
